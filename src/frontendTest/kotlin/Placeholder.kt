import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Placeholder : FunSpec(
    {
        // Needed as a placeholder to generate a report until we have at least one proper test
        test("1+1=2") {
            1 + 1 shouldBe 2
        }
    },
)
