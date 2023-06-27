package ar.edu.unlam.tallerweb1.delivery;

import ar.edu.unlam.tallerweb1.domain.mascotas.Mascota;
import ar.edu.unlam.tallerweb1.domain.mascotas.ServicioMascota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorMascotas {

    private ServicioMascota servicioMascota;

    @Autowired
    public ControladorMascotas(ServicioMascota servicioMascota) {
        this.servicioMascota = servicioMascota;
    }

    @RequestMapping(path = "/homeMascotas")
    public ModelAndView homeMascotas() {

        ModelMap modelo = new ModelMap();
        modelo.put("datosLogin", new DatosLogin());
        return new ModelAndView("pruebaMascotas", modelo);
    }

    @RequestMapping(path = "/mascotas", method = RequestMethod.GET)
    public ModelAndView getTodasLasMascotas() {
        ModelMap model = new ModelMap();
        List<Mascota> result = this.servicioMascota.ObtenerTodasLasMascotas();
        model.put("mascotas", result);
        return new ModelAndView("todas-las-mascotas", model);
    }

    @RequestMapping(path = "/mascotas/tipoMascota", method = RequestMethod.GET)
    public ModelAndView getMascotaPorTipo(Integer idTipoMascota) {
        ModelMap model = new ModelMap();
        List<Mascota> result = this.servicioMascota.ObtenerMascotasPorTipo(idTipoMascota);
        model.put("mascotas", result);

        // mapeada con la home pero faltaria definir con cual es
        return new ModelAndView("todas-las-mascotas", model);
    }

    @RequestMapping(path = "/mascota/detalle", method = RequestMethod.GET)
    public ModelAndView getDetalle(long id) {
        ModelMap model = new ModelMap();
        Mascota result = this.servicioMascota.ObtenerDetalle(id);
        model.put("mascota", result);

        // mapeada con la home pero faltaria definir con cual es
        return new ModelAndView("detalle-mascota", model);
    }

    @RequestMapping(path = "/buscar-mascotas")
    public ModelAndView irABuscarMascotas() {
        return new ModelAndView("pruebaMascotas");
    }

    @RequestMapping(path = "/mascotas/usuario")
    public ModelAndView getMascotaPorIdUsuario(long idUsuario) {
        ModelMap model = new ModelMap();
        List<Mascota> result = this.servicioMascota.obtenerMascotaPorIdUsuario(idUsuario);
        model.put("mascotas", result);
        return new ModelAndView("todas-las-mascotas", model);
    }

    @RequestMapping(path = "/mascotas/filtradas", method = RequestMethod.GET)
    public ResponseEntity<List<Mascota>> getMascotasFiltradas(@ModelAttribute DatosMascotasFiltradas request) {
        List<Mascota> result = this.servicioMascota.ObtenerMascotasFiltradas(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(path = "/mascotas/mis-mascotas")
    public ModelAndView getMascotasUsuario(long idUsuario) {
        ModelMap model = new ModelMap();
        List<Mascota> mascotas = this.servicioMascota.obtenerMascotaPorIdUsuario(idUsuario);
        model.put("mascotas", mascotas);
        return new ModelAndView("mis-mascotas", model);
    }

    @RequestMapping(value = "/mascotas/cercanas", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Mascota>> buscarMascotasCercanas(@RequestParam("latitud") String latitud,
                                                                           @RequestParam("longitud") String longitud,
                                                                           @RequestParam("radio") double radio,
                                                                           HttpSession session) {
        DatosMascotasFiltradas request = new DatosMascotasFiltradas();
        DatosUbicacion ubicacion = new DatosUbicacion();
        ubicacion.setLatitud(latitud);
        ubicacion.setLongitud(longitud);
        ubicacion.setRadio(radio);

        request.setUbicacion(ubicacion);

        // Guardar los datos en la sesión
        session.setAttribute("ubicacion", request.getUbicacion());
        session.setAttribute("radio", request.getUbicacion().getRadio());

        List<Mascota> response = this.servicioMascota.obtenerMascotasCercanas(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
