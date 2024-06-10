package nl.moukafih.demo.controller

import nl.moukafih.demo.DemoProperties
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController(private val properties: DemoProperties) {

    @GetMapping("/")
    fun index(model: Model): String {
        model["home_title"] = properties.title
        model["home_banner"] = properties.banner
        return "home/index"
    }
}