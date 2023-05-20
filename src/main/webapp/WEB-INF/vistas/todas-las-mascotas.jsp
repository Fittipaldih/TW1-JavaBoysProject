<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="es">

<head>
  <jsp:include page="head.jsp">
    <jsp:param name="head" value="head" />
  </jsp:include>
</head>
<body>

<jsp:include page="nav.jsp">
  <jsp:param name="nav" value="nav" />
</jsp:include>

<div class = "container">
  <h1>Bienvenidos a  todas las mascotas</h1>
</div>


<c:if test="${not empty mascotas}">
  <c:forEach  var="mascota" items="${mascotas}">
  <div class="card" style="width: 18rem;">
    <img src="img/${mascota.imagen}" class="card-img-top" alt="Mascota....">
    <div class="card-body">
      <h5 class="card-title">${mascota.nombre}</h5>
      <p class="card-text">Latitud: ${mascota.latitud}</p>
      <p class="card-text">Longitud: ${mascota.longitud}</p>
      <p class="card-text">Fecha de Adopcion: ${mascota.fechaAdopcion}</p>
      <form:form action="mascota/detalle" method="GET">
        <input name="id" value=${mascota.id} id=${mascota.id} type="hidden" class="form-control"/>
        <button class="dog-paw-button">Detalle</button>
      </form:form>
    </div>
  </div>
  </c:forEach>
</c:if>

<c:if test="${empty mascotas}">
  <h3><span>No hay Mascotas</span></h3>
  <br>
</c:if>

<jsp:include page="foot.jsp">
  <jsp:param name="foot" value="foot" />
</jsp:include>

</body>
</html>