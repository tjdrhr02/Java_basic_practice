public class HttpExample {
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
            }
            mainReqBody = sb.toString(); // Json String

            switch (path) {
                case "/saveinfo":
                    List<DataDto> incomingData = gson.fromJson(mainReqBody, new TypeToken<List<DataDto>>() {}.getType());
                    // JsonObject incomingData = JsonParser.parseString(mainReqBody).getJsonObject();

                    if (incomingData != null) {
                        storedList.addAll(incomingData);
                    }

                    mainResp.setStatus(HttpServletResponse.SC_OK);
                    break;

                case "/getinfo":
                    FilterDto filter = gson.fromJson(mainReqBody, FilterDto.class);

                    List<DataDto> filteredList;
                    synchronized (storedList) {
                        filteredList = storedList.stream()
                                .filter(d -> filter.name == null || filter.name.equals(d.name))
                                .collect(Collectors.toList());
                    }

                    mainRespBody = gson.toJson(filteredList);
                    mainResp.setContentType("application/json;charset=UTF-8");
                    mainResp.setStatus(HttpServletResponse.SC_OK);
                    mainResp.getWriter().write(mainRespBody);
                    break;

                case "/forwardinfo":
                    ForwardRequestDto forwardReq = gson.fromJson(mainReqBody, ForwardRequestDto.class);

                    String storedData;
                    synchronized (storedList) {
                        storedData = gson.toJson(storedList);
                    }
                    String targetUrl = String.format("http://%s:%d/receiveinfo", forwardReq.ip, forwardReq.port);
                    try {
                        ContentResponse subResp = httpClient.newRequest(targetUrl)
                                .method(HttpMethod.POST)
                                .content(new StringContentProvider(storedData), "application/json")
                                .send();

                        String subRespBody = subResp.getContentAsString();

//                        * Get
//                        // 쿼리 스트링 생성: 반드시 URL 인코딩 해야 함
//                        String queryString = String.format("param1=%s&param2=%s",
//                                URLEncoder.encode(param1, StandardCharsets.UTF_8),
//                                URLEncoder.encode(param2, StandardCharsets.UTF_8));
//
//                        // 최종 요청 URL: 쿼리 스트링 포함
//                        String targetUrl = baseUrl + "?" + queryString;
//
//                        // GET 요청 생성 및 전송 (body는 포함하지 않음)
//                        ContentResponse response = httpClient.newRequest(targetUrl)
//                                .method(HttpMethod.GET)
//                                .send();

                        mainRespBody = subRespBody;
                        mainResp.setContentType("application/json;charset=UTF-8");
                        mainResp.setStatus(HttpServletResponse.SC_OK);
                        mainResp.getWriter().write(mainRespBody);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    break;

                default:
                    mainResp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        }
    }

    // Dto Classes

}
