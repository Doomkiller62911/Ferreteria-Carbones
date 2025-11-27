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

import com.ferreteria_carbones_final_l.domain.Categoria;
import com.ferreteria_carbones_final_l.service.CategoriaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);
        model.addAttribute("totalCategorias", categorias.size());
        return "/categoria/listado";
    }

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/modificar/{idCategoria}")
    public String listado(@PathVariable() Integer idCategoria, Model model,
            RedirectAttributes redirectAttributes) {
        Optional<Categoria> categoriaOpt = categoriaService.getCategoria(idCategoria);
        if (categoriaOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("Error",
                    messageSource.getMessage("categoria.error01", null, Locale.getDefault()));
            return "redirect:/categoria/listado";
        }
        model.addAttribute("categoria", categoriaOpt.get());

        return "/categoria/modificar";

    }

    @PostMapping("/guardar")
    public String guardar(@Valid Categoria categoria, @RequestParam MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {
        categoriaService.save(categoria, imagenFile);
        redirectAttributes.addFlashAttribute("todoOK", messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/categoria/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idCategoria,
            RedirectAttributes redirectAttributes) {

        String titulo = "todoOK";
        String detalle = "mensaje.eliminado";
        try {
            categoriaService.delete(idCategoria);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            detalle = "categoria.error01";
        } catch (IllegalStateException e) {
            titulo = "error";
            detalle = "categoria.error02";
        } catch (Exception e) {
            titulo = "error";
            detalle = "categoria.error03";
        }
        redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(detalle, null, Locale.getDefault()));

        redirectAttributes.addFlashAttribute("todoOK", messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/categoria/listado";
    }

}
