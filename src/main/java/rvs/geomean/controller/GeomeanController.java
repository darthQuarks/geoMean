package rvs.geomean.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import rvs.geomean.datamodel.Point;
import rvs.geomean.datamodel.PointCollection;
import rvs.geomean.service.MeanPointService;

@Controller
@SessionAttributes(names = {"pointCollection", "meanPoint"})
public class GeomeanController {

    @Autowired
    private MeanPointService meanPointService;

    @GetMapping("/")
    public ModelAndView index(HttpSession session, Model model) {
        model.addAttribute("meanPoint", null);
        session.setAttribute("meanPoint", null);

        PointCollection pointCollection = new PointCollection();
        model.addAttribute("pointCollection", pointCollection);
        session.setAttribute("pointCollection", pointCollection);
        return new ModelAndView("index");
    }

    @PostMapping("/add_points")
    public ModelAndView addPoints(HttpSession session, Model model) {
        return new ModelAndView("index");
    }

    @GetMapping("/add_point")
    public ModelAndView addPoint(HttpSession session, Model model, @RequestParam String lat, @RequestParam String lon) {
        PointCollection pointCollection = (PointCollection) session.getAttribute("pointCollection");
        if (lat != null) {
            Point newPoint = new Point(lat, lon);
            pointCollection.add(newPoint);
            Point meanPoint = meanPointService.getMeanPoint(pointCollection);
            model.addAttribute("pointCollection", pointCollection);
            if (meanPoint != null) {
                session.setAttribute("meanPoint", meanPoint);
                model.addAttribute("meanPoint", meanPoint);
            }
        }
        return new ModelAndView("index");
    }

    @GetMapping("/remove")
    public ModelAndView removePoint(HttpSession session, Model model) {
        PointCollection pointCollection = (PointCollection) session.getAttribute("pointCollection");
        if (pointCollection.size() > 0) {
            pointCollection.remove(pointCollection.size() - 1);
            Point meanPoint = meanPointService.getMeanPoint(pointCollection);
            model.addAttribute("pointCollection", pointCollection);
            if (meanPoint != null) {
                session.setAttribute("meanPoint", meanPoint);
                model.addAttribute("meanPoint", meanPoint);
            }
        }
        return new ModelAndView("index");
    }

    @GetMapping("/error")
    public String getError(HttpSession session, Model model) {
        //model.addAttribute("message", "home");
        return "error/404";
    }
}
