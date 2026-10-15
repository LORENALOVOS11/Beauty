
/* =========================================
   BEAUTYSALON - JAVASCRIPT
   ========================================= */

document.addEventListener("DOMContentLoaded", function () {

    console.log("BeautySalon cargado correctamente.");

    const botonesReserva =
        document.querySelectorAll(".btn-reserva");

    botonesReserva.forEach(function (boton) {

        boton.addEventListener("click", function (evento) {

            evento.preventDefault();

            alert(
                "🌸 ¡Gracias por elegir BeautySalon!\n\n" +
                "El sistema de reservas estará disponible próximamente."
            );

        });

    });

});