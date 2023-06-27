package ar.edu.unlam.tallerweb1.delivery;

import ar.edu.unlam.tallerweb1.domain.mascotas.Mascota;
import ar.edu.unlam.tallerweb1.domain.mascotas.ServicioMascota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
        return new ModelAndView("todas-las-mascotas",model);
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
        return new ModelAndView("detalle-mascota", model);
    }

    @RequestMapping(path = "/buscar-mascotas")
    public ModelAndView irABuscarMascotas() {
        return new ModelAndView("pruebaMascotas");
    }

    @RequestMapping(path = "/mascotas/usuario")
    public ModelAndView getMascotaPorIdUsuario(int idUsuario) {
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
    public ModelAndView getMascotasUsuario(int idUsuario) {
        ModelMap model = new ModelMap();
        List<Mascota> mascotas = this.servicioMascota.obtenerMascotaPorIdUsuario(idUsuario);
        model.put("mascotas", mascotas);
        return new ModelAndView("mis-mascotas", model);
    }

}
