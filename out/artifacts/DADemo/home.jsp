<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://xmlns.jcp.org/jsf/html"
      xmlns:ui="http://xmlns.jcp.org/jsf/facelets"
      xmlns:f="http://xmlns.jcp.org/jsf/core">

<f:view>

    <h:head>
        <title>Login</title>
        <h:outputStylesheet library = "css" name = "styles.css"  />
    </h:head>

    <h:body class="background">


        <div class=" LoginColumn leftLoginColumn vertical-center">
            <h:form class="loginForm">
                <h1>Login</h1><br/>

                <h4>Email</h4>
                <h:inputText value = "Email"/><br/><br/>
                <h4>Password</h4>
                <h:inputText value = "Password"/><br/><br/>

                <h:commandButton action = "home" value = "LOGIN" style="margin-bottom: 10px" /><br/>
            </h:form>
        </div>


        <div class="LoginColumn rightLoginColumn vertical-center">
            <h:form class="loginForm">
                <h1>Sign Up</h1><br/>

                <h4>Name</h4>
                <h:inputText value = "Name"/><br/><br/>
                <h4>Last Name</h4>
                <h:inputText value = "Last name"/><br/><br/>
                <h4>Email</h4>
                <h:inputText value = "Email"/><br/><br/>
                <h4>Password</h4>
                <h:inputText value = "Password"/><br/><br/>

                <h:commandButton action = "home" value = "SIGN UP" style="margin-bottom: 10px" /><br/>
            </h:form>
        </div>
    </h:body>

</f:view>
</html>
