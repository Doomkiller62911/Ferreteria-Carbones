package com.ferreteria_carbones_final_l.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ferreteria_carbones_final_l.service.CategoriaService;
import com.ferreteria_carbones_final_l.service.ProductoService;

@Controller
public class IndexController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public IndexController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/")
    public String listado(Model model) {
        var productos = productoService.getProductos(false);
        model.addAttribute("productos", productos);

        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);

        return "/index";
    }

    @GetMapping("/consultas/{idCategoria}")
    public String listado(@PathVariable("idCategoria") Integer idCategoria, Model model) {
        var categoriaOpt = categoriaService.getCategoria(idCategoria);
        if (categoriaOpt.isEmpty()) {
            //Por alguna razón no encuentra la categoria...
            model.addAttribute("productos", java.util.Collections.EMPTY_LIST);
        } else {
            var categoria = categoriaOpt.get();
            var productos = categoria.getProductos();
            model.addAttribute("productos", productos);
        }
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);
        return "/index";
    }

}
