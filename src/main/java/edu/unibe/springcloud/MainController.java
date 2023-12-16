package edu.unibe.springcloud;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edu.unibe.springcloud.data.Etiquetado;
import edu.unibe.springcloud.data.EtiquetadoRepository;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class MainController {
    String AWS_BUCKET = "ejemplounibe";
    String BASE_URL_S3 = "http://"+AWS_BUCKET+".s3-website-us-east-1.amazonaws.com/";

    @Autowired
    private S3Template s3Template;

    @Autowired
    private EtiquetadoRepository etiquetadoRepository;

    @GetMapping("/lista")
    public String lista(Model model) {

        List<S3Resource> objects = s3Template.listObjects(AWS_BUCKET, "img");
        for(S3Resource obj:objects){
            System.out.println(">"+obj.getFilename());
            try {
                //System.out.println(ImaggaUtil.getTags(BASE_URL_S3+obj.getFilename()));

                JsonObject jsonObject = ImaggaUtil.getTags(BASE_URL_S3+obj.getFilename());
                JsonArray ja = jsonObject.getAsJsonObject("result").getAsJsonArray("tags");

                for(JsonElement je : ja.asList()){
                    JsonObject jo = je.getAsJsonObject();
                    BigDecimal confidence = jo.get("confidence").getAsBigDecimal();
                    String tag = jo.get("tag").getAsJsonObject().get("en").getAsString();
                    System.out.println("confidence: "+confidence);
                    System.out.println("tag:" + tag);

                    Etiquetado et = new Etiquetado();
                    et.setImagen(obj.getFilename());
                    et.setConfidence(confidence);
                    et.setTag(tag);
                    etiquetadoRepository.save(et);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }/**/
        }

        model.addAttribute("bucket ", AWS_BUCKET);
        return "lista";
    }






}
