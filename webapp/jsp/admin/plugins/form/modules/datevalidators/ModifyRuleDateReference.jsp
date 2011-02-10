<%@ page errorPage="../../../../ErrorPage.jsp" %>
<jsp:include page="../../../../AdminHeader.jsp" />

<jsp:useBean id="dateValidators" scope="session" class="fr.paris.lutece.plugins.form.modules.datevalidators.web.DateValidatorsJspBean" />

<% dateValidators.init( request, dateValidators.RIGHT_MANAGE_FORM ); %>
<%= dateValidators.getModifyRuleDateReference( request ) %>

<%@ include file="../../../../AdminFooter.jsp" %>
