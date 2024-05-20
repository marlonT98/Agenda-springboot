package com.app.agenda.agendaspringboot.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.agenda.agendaspringboot.modelo.Contacto;
import com.app.agenda.agendaspringboot.repositorio.ContactoRepositorio;

@Controller
public class ContactoControlador {

    @Autowired
    private ContactoRepositorio contactoRepositorio;

    // para ver la pagina de inicio
    @GetMapping({ "/", "" })
    public String verPaginaDeInicio(Model model) {

        List<Contacto> contactos = contactoRepositorio.findAll();// obtenemos la lista de contactos
        model.addAttribute("contactos", contactos);// pasamos la lista de contactos
        return "index";

    }

    // con esto vamos a mostrar el formulario para crear un nuevo contacto
    // lo que estamos pasando un modelo , osea es un nuevo objeto para que a ese
    // objeto le podamos asiganar valores a cada atributo que tiene.
    @GetMapping("/nuevo")
    public String mostrarFormularioDeRegistrarContacto(Model model) {

        model.addAttribute("contacto", new Contacto());
        return "nuevo";

    }

    @PostMapping("/nuevo")
    public String guardarContacto(
            @Validated Contacto contacto,
            BindingResult bindingResult,
            RedirectAttributes redirect,
            Model model) {

        if (bindingResult.hasErrors()) {// si es que tiene errores

            // yo lo pasare un nuevo formulario , no es que tenga una nueva instancia
            // si no de ese mismo objeto sacare los errores y voy a crear un nuevo
            // formulario para que vuelva a ingresar
            model.addAttribute("contacto", contacto);
            return "nuevo";

        }

        contactoRepositorio.save(contacto);
        redirect.addFlashAttribute("msgExito", "El contacto ha sido agregado con exito");

        return "redirect:/";// nos redirecciona al index

    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioDeEditarContacto(@PathVariable Integer id, Model model) {

        Optional<Contacto> contacto = contactoRepositorio.findById(id);

        if (contacto.isPresent()) {
            model.addAttribute("contacto", contacto.get());
            return "nuevo";
        } else {

            return "redirect:/";
        }

    }

    @PostMapping("/{id}/editar")
    public String actualizarContacto(
            @PathVariable Integer id,
            @Validated Contacto contacto,
            BindingResult bindingResult,
            RedirectAttributes redirect,
            Model model) {

        Optional<Contacto> contactoOp = contactoRepositorio.findById(id);

        if (contactoOp.isPresent()) {

            if (bindingResult.hasErrors()) {

                model.addAttribute("contacto", contacto);
                return "nuevo";

            }

            Contacto contactoDB = contactoOp.get();

            contactoDB.setNombre(contacto.getNombre());
            contactoDB.setCelular(contacto.getCelular());
            contactoDB.setEmail(contacto.getEmail());
            contactoDB.setFechaNacimiento(contacto.getFechaNacimiento());
          
            contactoRepositorio.save(contactoDB);

            redirect.addFlashAttribute("msgExito", "El contacto ha sido actualizado correctamente");

            return "redirect:/";// nos redirecciona al index
        } else {
            return "redirect:/";
        }
    }


    @PostMapping("/{id}/eliminar")
    public String EliminarContacto(@PathVariable Integer id,RedirectAttributes redirect) {

        Optional<Contacto> contacto = contactoRepositorio.findById(id);

        if (contacto.isPresent()) {
            contactoRepositorio.delete(contacto.get());
            redirect.addFlashAttribute("msgExito", "El contacto ha sido eliminado correctamente");
            return "redirect:/";
        } else {

            return "redirect:/";
        }

    }




}
