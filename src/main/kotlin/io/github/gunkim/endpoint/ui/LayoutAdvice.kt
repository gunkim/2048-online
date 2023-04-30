package io.github.gunkim.endpoint.ui

import com.samskivert.mustache.Mustache
import com.samskivert.mustache.Template
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute
import java.io.Writer


@ControllerAdvice
internal class LayoutAdvice(
    private val compiler: Mustache.Compiler
) {
    @ModelAttribute("layout")
    fun layout(model: Map<String, Any>): Mustache.Lambda {
        return Layout(compiler)
    }

    @ModelAttribute("title")
    fun title(@ModelAttribute layout: Layout): Mustache.Lambda {
        return Mustache.Lambda { frag, out ->
            layout.title = frag.execute()
        }
    }

    @ModelAttribute("content")
    fun content(@ModelAttribute layout: Layout): Mustache.Lambda {
        return Mustache.Lambda { frag, out ->
            layout.content = frag.execute()
        }
    }

    @ModelAttribute("css")
    fun css(@ModelAttribute layout: Layout): Mustache.Lambda {
        return Mustache.Lambda { frag, out ->
            layout.css = frag.execute()
        }
    }

    @ModelAttribute("js")
    fun js(@ModelAttribute layout: Layout): Mustache.Lambda {
        return Mustache.Lambda { frag, out ->
            layout.js = frag.execute()
        }
    }
}

internal class Layout(
    private val compiler: Mustache.Compiler,
    var title: String? = null,
    var content: String? = null,
    var css: String? = null,
    var js: String? = null
) : Mustache.Lambda {
    override fun execute(frag: Template.Fragment, out: Writer) {
        frag.execute()
        compiler.compile("{{>layout}}").execute(frag.context(), out)
    }
}
