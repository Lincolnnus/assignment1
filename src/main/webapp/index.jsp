<h1>Hello World App</h1>
<% if (session.getAttribute("mysession")!= null){ %>
	<%= session.getAttribute("username") %>
	<a href="/logout">Logout</a>
<% } else { %>
	<a href="/login">Login</a>
<% } %>
