package it.juan.user.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.juan.user.entity.Habitacion;
import it.juan.user.entity.Hotel;
import it.juan.user.entity.User;
import it.juan.user.service.HotelService;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Hoteles y habitaciones", description = "Lista de hoteles con sus respectivas habitaciones")
@RequestMapping("/api") //esta sera la raiz de la url, es decir http://127.0.0.1:5000/api/

public class HotelRestController {

    //Inyectamos el servicio para poder hacer uso de el
    @Autowired
    private HotelService hotelService;

    //PARA EL JWT al meter user y password en params
    //http://127.0.0.1:5000/api_hoteles/user?user=juan&password=juan
    //Luego escribir en el Header autorizacion y el token que te da para poder realizar los métodos
    @Operation(summary = "Genera un JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genera un Bearer", content = @Content(schema = @Schema(implementation = User.class)))
    })
    @PostMapping("/user")
    public User login(@RequestParam("username") String username, @RequestParam("password") String pwd) {

        if ((username.equals("juan")) && (pwd.equals("juan"))) {
            System.out.println("Me crea el token");
            String token = getJWTToken(username);
            User user = new User();
            user.setUser(username);
            user.setPwd(pwd);
            user.setToken(token);

            return user;
        } else

            return null;

    }
    //Utilizamos el método getJWTToken(...) para construir el token,
    // delegando en la clase de utilidad Jwts que incluye información sobre su expiración
    // y un objeto de GrantedAuthority de Spring que, como veremos más adelante,
    // usaremos para autorizar las peticiones a los recursos protegidos.

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 9000000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

    /*Este método se hará cuando por una petición GET (como indica la anotación) se llame a la url
    http://127.0.0.1:5000/api_hoteles/hotel*/
    @Operation(summary = "Obtiene el listado de hoteles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de hoteles",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Hotel.class)))),
    })
    @GetMapping("/hoteles")
    public List<Hotel> findAll(){
        //retornará todos los usuarios
        return hotelService.findAll();
    }



    /*Este método se hará cuando por una petición POST (como indica la anotación) se llame a la url
    http://127.0.0.1:5000/api_hoteles/hotel/  */
    @Operation(summary = "Registra un nuevo hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se registra el hotel", content = @Content(schema = @Schema(implementation = Hotel.class)))
    })
    @PostMapping("/insertar_hotel")
    public String addHotel(@RequestBody Hotel hotel) {
        hotel.setIdHotel(0);

        // Este método guardará al hotel enviado
        hotelService.save(hotel);

        // Devolver un mensaje de éxito
        return "Se registra el hotel";
    }


    //Hoteles por categoria
    //api_hoteles/hotelesPorCategoria?categoria=4
    @Operation(summary = "Obtiene un hotel filtrado por su categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el hotel", content = @Content(schema = @Schema(implementation = Hotel.class))),
            @ApiResponse(responseCode = "404", description = "El hotel no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/buscar_hotel_categoria/{categoria}")
    public List<Hotel> getHotelesPorCategoria(@PathVariable String categoria) {
        List<Hotel> hoteles=hotelService.findByCategoria(categoria);
        return hoteles;
    }

    // Métodos relacionados con Habitacion
    @Operation(summary = "Lista de hoteles por localidad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hoteles encontrados", content = @Content(schema = @Schema(implementation = Hotel.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron hoteles para la localidad especificada", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/buscar_hotel_localidad/{localidad}")
    public List<Hotel> findByLocalidad(@PathVariable String localidad) {
        return (List<Hotel>) hotelService.findByLocalidad(localidad);
    }



    //http://127.0.0.1:5000/api_hoteles/habitacion
    @Operation(summary = "Registra una nueva habitación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se registra la habitación", content = @Content(schema = @Schema(implementation = Habitacion.class)))
    })
    @PostMapping("/insertar_habitacion")
    public Habitacion addHabitacion(@RequestBody Habitacion habitacion) {
        habitacion.setIdHabitacion(0);
        hotelService.saveHabitacion(habitacion);
        return habitacion;
    }

    //http://127.0.0.1:5000/api_hoteles/habitacion
    @Operation(summary = "Modifica la habitación en el catálogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se modifica la habitación", content = @Content(schema = @Schema(implementation = Habitacion.class))),
            @ApiResponse(responseCode = "404", description = "La habitación no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping("/modificar_ocupacion/")
    public Habitacion updateHabitacion(@RequestBody Habitacion habitacion) {
        hotelService.saveHabitacion(habitacion);
        return habitacion;
    }

    //http://127.0.0.1:8080/api_hoteles/habitacion/1
    @Operation(summary = "Elimina una habitación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina la habitación", content = @Content(schema = @Schema(implementation = Habitacion.class))),
            @ApiResponse(responseCode = "404", description = "La habitación no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping("/eliminar_habitacion/{idHabitacion}")
    public String deleteHabitacion(@PathVariable int idHabitacion) {
        Habitacion habitacion = hotelService.findHabitacionById(idHabitacion);
        if (habitacion == null) {
            throw new RuntimeException("Habitacion id not found - " + idHabitacion);
        }
        hotelService.deleteHabitacionById(idHabitacion);
        return "Deleted habitacion id - " + idHabitacion;
    }



    @GetMapping("/listar_tamano_precio")
    public List<Habitacion> habitaciones_Tamano_Precio(
            @RequestParam double capacidad_Minima,
            @RequestParam double capacidad_Maxima,
            @RequestParam double precio_Minimo,
            @RequestParam double precio_Maximo)
    {
        return hotelService.habitaciones_Tamano_Precio(capacidad_Minima, capacidad_Maxima, precio_Minimo, precio_Maximo);
    }


}
