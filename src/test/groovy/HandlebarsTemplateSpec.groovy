import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

class HandlebarsTemplateSpec extends Specification {
    @Shared
    ScriptEngine engine = new ScriptEngineManager().getEngineByName('nashorn')

    private loadJS(String path) {
        def source = this.class.getResource(path).text
        engine.eval(source)
    }

    void setupSpec() {
        loadJS('/js/lib/handlebars-v1.3.0.js')
        loadJS('/js/HandlebarsTemplateRenderer.js')
    }

    @Unroll
    def "template"() {
        setup:
        def template = this.class.getResource('handlebars/greeting.handlebars').text

        expect:
        engine.invokeFunction('render', template, model) == expectedOutput

        where:
        model                                  || expectedOutput
        [name: [first: 'James', last: 'Bond']] || 'The name is Bond. James Bond.'
        [name: [last: 'Bond']]                 || 'The name is Bond.  Bond.'
    }
}
