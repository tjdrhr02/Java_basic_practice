public class HttpBasic {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        HttpClient httpClient = new HttpClient();
        httpClient.start();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addServlet(new ServletHolder(new MyServlet(httpClient)), "/*");

        server.setHandler(context);
        server.start();
        server.join();

        httpClient.stop();
    }

    static class MyServlet extends HttpServlet {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        HttpClient httpClient;

        public MyServlet(HttpClient httpClient) {
            this.httpClient = httpClient;
        }

        @Override
        protected void doPost(HttpServletRequest mainReq, HttpServletResponse mainResp) throws ServletException, IOException {
            String mainReqBody; // Json String
            String mainRespBody; // Json String

            String path = mainReq.getPathInfo();

            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = mainReq.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            mainReqBody = sb.toString(); // Json String

            switch (path) {
                case "/basic":
                    // 1. Java 객체로 파싱 - mainReq

                    // 2. 로직

                    // 3. (Json String으로 응답 - mainResp)
                    mainRespBody = gson.toJson(?);
                    mainResp.setContentType("application/json;charset=UTF-8");
                    mainResp.setStatus(HttpServletResponse.SC_OK);
                    mainResp.getWriter().write(mainRespBody);
                    break;

                case "/getinfo":
                    break;

                default:
                    mainResp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        }

        protected void doGet(HttpServletRequest mainReq, HttpServletResponse mainResp) throws ServletException, IOException {
            String name = mainReq.getParameter("name");
            String age = mainReq.getParameter("age");

        }
    }
}
