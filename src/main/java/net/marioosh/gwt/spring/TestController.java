package net.marioosh.gwt.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
  
  @RequestMapping("/start")
  public String test() {
    return "start";
  }
}

