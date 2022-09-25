#language: es

Característica: Nombre de moneda
    Como financiero
    necesito convertir códigos de moneda ISO a lenguaje forma
    para agilizar el proceso de comprensión de los clientes.

  Escenario: Convertir de moneda ISO a nombre formal
    Dado que el usuario está en el recurso web indicando el nombre de moneda ISO "COP"
    Cuando el usuario genera la consulta
    Entonces visualizará el nombre formal de moneda como "Pesos"