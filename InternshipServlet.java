import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/internships")
public class InternshipServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final List<Internship> INTERNSHIPS = Arrays.asList(
        new Internship(
            1,
            "Java Programming Intern",
            "TechNova Solutions",
            "Java",
            "Remote",
            "₹5,000 / month",
            "3 months"
        ),

        new Internship(
            2,
            "Frontend Web Developer Intern",
            "PixelCraft Labs",
            "Web Development",
            "Hybrid",
            "₹8,000 / month",
            "4 months"
        ),

        new Internship(
            3,
            "Data Analyst Intern",
            "InsightWorks",
            "Data",
            "On-site",
            "₹10,000 / month",
            "6 months"
        ),

        new Internship(
            4,
            "Cyber Security Intern",
            "SecureGrid",
            "Cyber Security",
            "Remote",
            "₹7,000 / month",
            "3 months"
        )
    );

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = safe(request.getParameter("keyword"));
        String mode = safe(request.getParameter("mode"));
        String category = safe(request.getParameter("category"));

        List<Internship> results = new ArrayList<Internship>();

        for (Internship item : INTERNSHIPS) {

            boolean matchesKeyword =
                    keyword.isEmpty()
                    || item.getTitle().toLowerCase().contains(keyword.toLowerCase())
                    || item.getCompany().toLowerCase().contains(keyword.toLowerCase())
                    || item.getCategory().toLowerCase().contains(keyword.toLowerCase());

            boolean matchesMode =
                    mode.isEmpty()
                    || item.getMode().equalsIgnoreCase(mode);

            boolean matchesCategory =
                    category.isEmpty()
                    || item.getCategory().equalsIgnoreCase(category);

            if (matchesKeyword && matchesMode && matchesCategory) {
                results.add(item);
            }
        }

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");

        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Internship Results</title>");
        out.println("<link rel='stylesheet' href='style.css'>");
        out.println("</head>");

        out.println("<body>");

        // Skip link
        out.println("<a class='skip-link' href='#main'>Skip to main content</a>");

        // Header
        out.println("<header class='site-header'>");
        out.println("<div class='container header-inner'>");

        out.println("<a class='brand' href='index.html'>");
        out.println("Internship<span>Hub</span>");
        out.println("</a>");

        out.println("<nav aria-label='Primary navigation'>");
        out.println("<a href='index.html'>Home</a>");
        out.println("<a href='index.html#about'>About</a>");
        out.println("</nav>");

        out.println("</div>");
        out.println("</header>");

        // Main content
        out.println("<main id='main'>");
        out.println("<section class='listing-section'>");
        out.println("<div class='container'>");

        out.println("<div class='section-heading'>");

        out.println("<div>");
        out.println("<p class='eyebrow'>SEARCH RESULTS</p>");
        out.println("<h1>Available internships</h1>");
        out.println("</div>");

        out.println("<a class='text-link' href='index.html'>New search</a>");

        out.println("</div>");

        // No results
        if (results.isEmpty()) {

            out.println("<div class='card'>");
            out.println("<h2>No internships found</h2>");
            out.println("<p>");
            out.println("Try a broader keyword or choose 'All' in the filters.");
            out.println("</p>");
            out.println("</div>");

        } else {

            out.println("<div class='grid'>");

            for (Internship item : results) {

                out.println("<article class='card'>");

                out.println("<div class='card-top'>");

                out.println("<span class='tag'>"
                        + esc(item.getCategory())
                        + "</span>");

                out.println("<span class='mode'>"
                        + esc(item.getMode())
                        + "</span>");

                out.println("</div>");

                out.println("<h2>"
                        + esc(item.getTitle())
                        + "</h2>");

                out.println("<p class='company'>"
                        + esc(item.getCompany())
                        + "</p>");

                out.println("<ul class='meta' aria-label='Internship details'>");

                out.println("<li>"
                        + esc(item.getStipend())
                        + "</li>");

                out.println("<li>"
                        + esc(item.getDuration())
                        + "</li>");

                out.println("<li>Student-friendly opportunity</li>");

                out.println("</ul>");

                out.println(
                    "<a class='btn secondary' href='internship?id="
                    + item.getId()
                    + "'>View internship</a>"
                );

                out.println("</article>");
            }

            out.println("</div>");
        }

        out.println("</div>");
        out.println("</section>");
        out.println("</main>");

        // Footer
        out.println("<footer class='footer'>");
        out.println("<div class='container'>");
        out.println("<p>© 2026 InternshipHub.</p>");
        out.println("</div>");
        out.println("</footer>");

        out.println("</body>");
        out.println("</html>");
    }

    // Safely read request parameters
    private static String safe(String value) {
        return value == null ? "" : value.trim();
    }

    // Prevent HTML injection
    private static String esc(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    // Internship class
    private static class Internship {

        private int id;
        private String title;
        private String company;
        private String category;
        private String mode;
        private String stipend;
        private String duration;

        public Internship(
                int id,
                String title,
                String company,
                String category,
                String mode,
                String stipend,
                String duration) {

            this.id = id;
            this.title = title;
            this.company = company;
            this.category = category;
            this.mode = mode;
            this.stipend = stipend;
            this.duration = duration;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getCompany() {
            return company;
        }

        public String getCategory() {
            return category;
        }

        public String getMode() {
            return mode;
        }

        public String getStipend() {
            return stipend;
        }

        public String getDuration() {
            return duration;
        }
    }
}
```
