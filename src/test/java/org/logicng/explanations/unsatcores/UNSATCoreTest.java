package org.logicng.explanations.unsatcores;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.logicng.formulas.FormulaFactory;
import org.logicng.io.parsers.ParserException;
import org.logicng.io.parsers.PropositionalParser;
import org.logicng.propositions.Proposition;
import org.logicng.propositions.StandardProposition;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for {@link UNSATCore}.
 * @version 1.2
 * @since 1.1
 */
public class UNSATCoreTest {

  @Rule
  public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

  private final List<Proposition> props1;
  private final List<Proposition> props2;
  private final UNSATCore core1;
  private final UNSATCore core2;


  public UNSATCoreTest() throws ParserException {
    props1 = new ArrayList<>();
    props2 = new ArrayList<>();
    FormulaFactory f = new FormulaFactory();
    PropositionalParser parser = new PropositionalParser(f);
    props1.add(new StandardProposition(parser.parse("a | b")));
    props1.add(new StandardProposition(parser.parse("~a | b")));
    props1.add(new StandardProposition(parser.parse("a | ~b")));
    props1.add(new StandardProposition(parser.parse("~a | ~b")));
    props2.add(new StandardProposition(parser.parse("a | b")));
    props2.add(new StandardProposition(parser.parse("~a | b")));
    props2.add(new StandardProposition(parser.parse("a | ~b")));
    props2.add(new StandardProposition(parser.parse("~a | ~b")));
    props2.add(new StandardProposition(parser.parse("~a | ~b | c")));
    this.core1 = new UNSATCore(props1, true);
    this.core2 = new UNSATCore(props2, false);
  }

  @Test
  public void testGetters() {
    softly.assertThat(props1).isEqualTo(core1.propositions());
    softly.assertThat(props2).isEqualTo(core2.propositions());
    softly.assertThat(core1.isMUS()).isTrue();
    softly.assertThat(core2.isMUS()).isFalse();
  }

  @Test
  public void testHashCode() {
    softly.assertThat(core1.hashCode()).isEqualTo(core1.hashCode());
    softly.assertThat(new UNSATCore(props2, false).hashCode()).isEqualTo(core2.hashCode());
  }

  @Test
  public void testEquals() {
    softly.assertThat(core1).isEqualTo(core1);
    softly.assertThat(new UNSATCore(props1, true)).isEqualTo(core1);
    softly.assertThat(core1).isNotEqualTo(core2);
    softly.assertThat(core1).isNotEqualTo(new UNSATCore(props1, false));
    softly.assertThat(core1).isNotEqualTo(new UNSATCore(props2, true));
    softly.assertThat(core1).isNotEqualTo(null);
    softly.assertThat(core1).isNotEqualTo("String");
  }

  @Test
  public void testToString() {
    final String exp1 = "UNSATCore{isMUS=true, propositions=[StandardProposition{formulas=AND[a | b], description=}, " +
            "StandardProposition{formulas=AND[~a | b], description=}, StandardProposition{formulas=AND[a | ~b], " +
            "description=}, StandardProposition{formulas=AND[~a | ~b], description=}]}";
    final String exp2 = "UNSATCore{isMUS=false, propositions=[StandardProposition{formulas=AND[a | b], description=}, " +
            "StandardProposition{formulas=AND[~a | b], description=}, StandardProposition{formulas=AND[a | ~b], " +
            "description=}, StandardProposition{formulas=AND[~a | ~b], description=}, " +
            "StandardProposition{formulas=AND[~a | ~b | c], description=}]}";
    softly.assertThat(core1.toString()).isEqualTo(exp1);
    softly.assertThat(core2.toString()).isEqualTo(exp2);
  }

}
