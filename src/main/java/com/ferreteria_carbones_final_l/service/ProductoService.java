package com.tienda_l.service;
import com.tienda_l.domain.Producto;
import com.tienda_l.repository.ProductoRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Transactional(readOnly =true)
    public List<Producto> getProductos(boolean activo) {
        if (activo) {
            return productoRepository.findByActivoTrue();
        }
        return productoRepository.findAll();
    }
    
    @Transactional(readOnly =true)
    public Optional<Producto> getProducto(Integer idProducto) {
        return productoRepository.findById(idProducto);
    
    }
    
    @Autowired
    private FirebaseStorageService firebaseStorageService;
    
    @Transactional
    public void save (Producto producto, MultipartFile imagenFile){
        producto = productoRepository.save(producto);
        if (!imagenFile.isEmpty()){
            try {
                String rutaImagen = firebaseStorageService.uploadImage(
                        imagenFile,
                        "producto",
                        producto.getIdProducto());
                producto.setRutaImagen(rutaImagen);
                productoRepository.save(producto);
            } catch(IOException ex){
                
            }
        }
        
    }
    
    @Transactional
    public void delete(Integer idProducto){
        if (!productoRepository.existsById(idProducto)){
            throw new IllegalArgumentException("La categoría no existe. ID:"+idProducto); 
        }
        try {
            productoRepository.deleteById(idProducto);
        } catch (DataIntegrityViolationException ex){
            throw new IllegalStateException("No se puede eliminar pues tiene datos asociados");
        }
        
    }
            
}
