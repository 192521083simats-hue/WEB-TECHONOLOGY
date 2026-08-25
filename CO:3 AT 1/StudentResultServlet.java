import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student-result")
public class StudentResultServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String name = request.getParameter("name");
        String registerNumber = request.getParameter("registerNumber");
        String subject1Text = request.getParameter("subject1");
        String subject2Text = request.getParameter("subject2");
        String subject3Text = request.getParameter("subject3");

        String errorMessage = null;
        int subject1 = 0;
        int subject2 = 0;
        int subject3 = 0;

        if (isBlank(name) || isBlank(registerNumber) || isBlank(subject1Text)
                || isBlank(subject2Text) || isBlank(subject3Text)) {
            errorMessage = "All fields are required.";
        } else {
            try {
                subject1 = Integer.parseInt(subject1Text);
                subject2 = Integer.parseInt(subject2Text);
                subject3 = Integer.parseInt(subject3Text);
            } catch (NumberFormatException exception) {
                errorMessage = "Marks must be valid whole numbers.";
            }
        }

        if (errorMessage == null && (!isValidMark(subject1) || !isValidMark(subject2) || !isValidMark(subject3))) {
            errorMessage = "Each mark must be between 0 and 100.";
        }

        try (PrintWriter out = response.getWriter()) {
            if (errorMessage != null) {
                printMessage(out, "Unable to process result", errorMessage);
                return;
            }

            int total = subject1 + subject2 + subject3;
            double average = total / 3.0;
            int highestMark = Math.max(subject1, Math.max(subject2, subject3));
            String status = average >= 40 && subject1 >= 40 && subject2 >= 40 && subject3 >= 40
                    ? "Pass" : "Fail";

            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("    <meta charset=\"UTF-8\">");
            out.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
            out.println("    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">");
            out.println("    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>");
            out.println("    <link href=\"https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&family=Plus+Jakarta+Sans:wght@400;500;600;700;800&display=swap\" rel=\"stylesheet\">");
            out.println("    <link rel=\"stylesheet\" href=\"style.css\">");
            out.println("    <title>Student Result</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("    <main class=\"page-shell result-page\">");
            out.println("        <p class=\"eyebrow\">Processed successfully</p>");
            out.println("        <h1>Student Result</h1>");
            out.println("        <p class=\"student-name\">" + escapeHtml(name) + " <span>" + escapeHtml(registerNumber) + "</span></p>");
            out.println("        <section class=\"result-grid\">");
            out.println("            <div><span>Total</span><strong>" + total + " / 300</strong></div>");
            out.println("            <div><span>Average</span><strong>" + String.format(Locale.ROOT, "%.2f", average) + "%</strong></div>");
            out.println("            <div><span>Highest mark</span><strong>" + highestMark + "</strong></div>");
            out.println("            <div class=\"status " + status.toLowerCase(Locale.ROOT) + "\"><span>Status</span><strong>" + status + "</strong></div>");
            out.println("        </section>");
            out.println("        <a class=\"back-link\" href=\"index.html\">");
            out.println("            <svg width=\"18\" height=\"18\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2.5\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><line x1=\"19\" y1=\"12\" x2=\"5\" y2=\"12\"></line><polyline points=\"12 19 5 12 12 5\"></polyline></svg>");
            out.println("            Process another result");
            out.println("        </a>");
            out.println("    </main>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private boolean isValidMark(int mark) {
        return mark >= 0 && mark <= 100;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void printMessage(PrintWriter out, String title, String message) {
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("    <meta charset=\"UTF-8\">");
        out.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">");
        out.println("    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>");
        out.println("    <link href=\"https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&family=Plus+Jakarta+Sans:wght@400;500;600;700;800&display=swap\" rel=\"stylesheet\">");
        out.println("    <link rel=\"stylesheet\" href=\"style.css\">");
        out.println("    <title>" + escapeHtml(title) + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <main class=\"page-shell result-page\">");
        out.println("        <p class=\"eyebrow\">Validation message</p>");
        out.println("        <h1>" + escapeHtml(title) + "</h1>");
        out.println("        <p class=\"error-message\">" + escapeHtml(message) + "</p>");
        out.println("        <a class=\"back-link\" href=\"index.html\">");
        out.println("            <svg width=\"18\" height=\"18\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2.5\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><line x1=\"19\" y1=\"12\" x2=\"5\" y2=\"12\"></line><polyline points=\"12 19 5 12 12 5\"></polyline></svg>");
        out.println("            Return to form");
        out.println("        </a>");
        out.println("    </main>");
        out.println("</body>");
        out.println("</html>");
    }

    private String escapeHtml(String value) {
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
