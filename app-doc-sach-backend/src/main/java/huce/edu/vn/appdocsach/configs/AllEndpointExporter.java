package huce.edu.vn.appdocsach.configs;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AllEndpointExporter implements ApplicationListener<ContextRefreshedEvent> {

    // private final String currentDirectory = System.getProperty("user.dir");
    // private final String fileName = "APIs.curl";

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {

        // ApplicationContext applicationContext = event.getApplicationContext();
        // Map<RequestMappingInfo, HandlerMethod> map = 
        //     applicationContext.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class)
        //     .getHandlerMethods();

        // StringBuilder curlCommands = new StringBuilder();
        // for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
        //     RequestMappingInfo requestMappingInfo = entry.getKey();
        //     HandlerMethod handlerMethod = entry.getValue();
        //     curlCommands.append("curl ")
        //                 .append(requestMappingInfo.getMethodsCondition().getMethods().toString().replaceAll("\\[]", ""))
        //                 .append(requestMappingInfo.getActivePatternsCondition())
        //                 .append(System.lineSeparator());
        // }
        // log.info(curlCommands.toString());

        // try {
        //     Path apiFilePath = Paths.get(currentDirectory, fileName);
        //     Files.write(apiFilePath, curlCommands.toString().getBytes());
        //     System.out.println("Curl commands written to " + apiFilePath.toString());
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }
}