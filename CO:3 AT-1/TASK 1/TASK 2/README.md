# Assessment 2: Student Result Processing

This is a Jakarta Servlet web application. It cannot be run by opening `index.html` directly because the `student-result` URL is handled by a servlet container.

## Run with Apache Tomcat 10+

1. Install Apache Tomcat 10.1 and Java.
2. Copy the `Assessment2` folder into Tomcat's `webapps` folder and rename it to `Assessment2` if needed.
3. Compile `StudentResultServlet.java` with the `jakarta.servlet-api` JAR supplied by Tomcat:

```powershell
javac -cp "$env:CATALINA_HOME\lib\servlet-api.jar" -d "Assessment2\WEB-INF\classes" "Assessment2\StudentResultServlet.java"
```

4. Start Tomcat.
5. Open:C:\Users\GOKUL.K\OneDrive\Desktop\apache-tomcat-11.0.11

```text
http://localhost:8080/Assessment2/index.html
```

The form submits to `/student-result`, which is mapped in `WEB-INF/web.xml` and with `@WebServlet`.
