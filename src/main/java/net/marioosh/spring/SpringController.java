package net.marioosh.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpringController {
  
  @RequestMapping("/start")
  public String test() {
    return "start";
  }
}

