package com.ferreteria_carbones_final_l.Controller;

import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ferreteria_carbones_final_l.domain.Producto;
import com.ferreteria_carbones_final_l.service.CategoriaService;
import com.ferreteria_carbones_final_l.service.ProductoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var productos = productoService.getProductos(false);
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());

        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);

        return "/producto/listado";
    }

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/modificar/{idProducto}")
    public String listado(@PathVariable() Integer idProducto, Model model,
            RedirectAttributes redirectAttributes) {
        Optional<Producto> productoOpt = productoService.getProducto(idProducto);
        if (productoOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("Error",
                    messageSource.getMessage("producto.error01", null, Locale.getDefault()));
            return "redirect:/producto/listado";
        }
        model.addAttribute("producto", productoOpt.get());

        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);

        return "/producto/modificar";

    }

    @PostMapping("/guardar")
    public String guardar(@Valid Producto producto, @RequestParam MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {
        productoService.save(producto, imagenFile);
        redirectAttributes.addFlashAttribute("todoOK", messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/producto/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idProducto,
            RedirectAttributes redirectAttributes) {

        String titulo = "todoOK";
        String detalle = "mensaje.eliminado";
        try {
            productoService.delete(idProducto);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            detalle = "producto.error01";
        } catch (IllegalStateException e) {
            titulo = "error";
            detalle = "producto.error02";
        } catch (Exception e) {
            titulo = "error";
            detalle = "producto.error03";
        }
        redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(detalle, null, Locale.getDefault()));

        redirectAttributes.addFlashAttribute("todoOK", messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/producto/listado";
    }

}
