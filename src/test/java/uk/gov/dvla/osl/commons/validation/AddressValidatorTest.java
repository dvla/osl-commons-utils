package uk.gov.dvla.osl.commons.validation;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddressValidatorTest {

    @Test
    public void testAddress_Valid_Address_Line_One_Char(){
        String line1 = "1";
        assertThat(AddressValidator.isValidLineFormat(line1), is(true));
    }

    @Test
    public void testAddress_Valid_Line(){
        String line1 = "1 Road Street";
        assertThat(AddressValidator.isValidLineFormat(line1), is(true));
    }

    @Test
    public void testAddress_Valid_Line_Special_Chars(){
        String line1 = " ,.()-&'/";
        assertThat(AddressValidator.isValidLineFormat(line1), is(true));
    }

    @Test
    public void testAddress_Invalid_Line_Empty(){
        String line1 = "";
        assertThat(AddressValidator.isValidLineFormat(line1), is(false));
    }

    @Test
    public void testAddress_Invalid_Line_More_Than_30_Chars(){
        String line1 = "12345679890 Qwertyu Asdfghjklzx";
        assertThat(AddressValidator.isValidLineFormat(line1), is(false));
    }

    @Test
    public void testAddress_Valid_Line_Closed_Brackets(){
        String line1 = "(1 Street Road )";
        assertThat(AddressValidator.isValidLineFormat(line1), is(true));
        line1 = "1 Street (Road) Rd";
        assertThat(AddressValidator.isValidLineFormat(line1), is(true));
    }

    @Test
    public void testAddress_Invalid_Line_Unclosed_Brackets(){
        String line1 = "1 Street ( Road";
        assertThat(AddressValidator.isValidLineFormat(line1), is(false));
    }

    @Test
    public void testAddress_Valid_Post_Town_One_Char(){
        String town = "A";
        assertThat(AddressValidator.isValidPostTownFormat(town), is(true));
    }

    @Test
    public void testAddress_Valid_Post_Town(){
        String town = "London";
        assertThat(AddressValidator.isValidPostTownFormat(town), is(true));
    }

    @Test
    public void testAddress_Valid_Post_Town_Special_Chars(){
        String town = " ,.()-&'/";
        assertThat(AddressValidator.isValidPostTownFormat(town), is(true));
    }

    @Test
    public void testAddress_Invalid_Town_Empty(){
        String town = "";
        assertThat(AddressValidator.isValidPostTownFormat(town), is(false));
    }

    @Test
    public void testAddress_Invalid_Town_More_Than_30_Chars(){
        String town = "ZXCuwsaksd Qwertyu Asdfghjklzxc";
        assertThat(AddressValidator.isValidPostTownFormat(town), is(false));
    }

    @Test
    public void testAddress_Invalid_Town_Digits(){
        String town = "Lond0n";
        assertThat(AddressValidator.isValidPostTownFormat(town), is(false));
    }

    @Test
    public void testAddress_Valid_Town_Closed_Brackets(){
        String line1 = "(Swansea )";
        assertThat(AddressValidator.isValidLineFormat(line1), is(true));
        line1 = "This (Town) Ghost Town";
        assertThat(AddressValidator.isValidLineFormat(line1), is(true));
    }

    @Test
    public void testAddress_Invalid_Town_Unclosed_Brackets(){
        String line1 = "This ( Town";
        assertThat(AddressValidator.isValidLineFormat(line1), is(false));
    }

    @Test
    public void testPostcode_Valid_postcode_withoutSpace_between_outward_and_inward(){
        String postcode = "M11AA";
        validatePostcode(postcode, true);
    }
    @Test
    public void testPostcode_Valid_postcode_OneSpace_between_outward_and_inward(){
        String postcode = "M1 1AA";
        validatePostcode(postcode, true);
    }
    @Test
    public void testPostcode_InValid_postcode_moreThanOneSpace_between_outward_and_inward(){
        String postcode = "M1  1AA";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_Valid_inward(){
        String postcode = "W2 6AZ";
        validatePostcode(postcode, true);
    }
    @Test
    public void testPostcode_InValid_inward_AAA(){
        String postcode = "W2 bAZ";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_inward_NNA(){
        String postcode = "W2 33Z";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_inward_containing_V(){
        String postcode = "W2 3VZ";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_inward_containing_C(){
        String postcode = "W2 3CC";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_inward_containing_O(){
        String postcode = "W2 3oZ";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_inward_containing_K(){
        String postcode = "W2 3OK";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_inward_containing_I(){
        String postcode = "W2 3ZI";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_Valid_outward_AN(){
        String postcode = "W2 6AZ";
        validatePostcode(postcode, true);
    }
    @Test
    public void testPostcode_InValid_outward_aa(){
        String postcode = "mn 1aa";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_outward_NA(){
        String postcode = "1n1aa";
        validatePostcode(postcode, false);
    }

    @Test
    public void testPostcode_Valid_outward_ANN(){
        String postcode = "M60 1NW";
        validatePostcode(postcode, true);
    }
    @Test
    public void testPostcode_Valid_outward_AAN(){
        String postcode = "CR2 6XH";
        validatePostcode(postcode, true);
    }
    @Test
    public void testPostcode_Valid_outward_ANA(){
        String postcode = "C2a 6XH";
        validatePostcode(postcode, true);
    }
    @Test
    public void testPostcode_InValid_outward_NAN(){
        String postcode = "1R2 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_Valid_outward_AANN(){
        String postcode = "DN55 1PT";
        validatePostcode(postcode, true);
    }
    @Test
    public void testPostcode_Valid_outward_AANA(){
        String postcode = "DN5b 1PT";
        validatePostcode(postcode, true);
    }

    @Test
    public void testPostcode_InValid_Outward_Q1_include_Letter_Q(){
        String postcode = "Q1 1AA";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_Q1_include_Letter_V(){
        String postcode = "V1 1AA";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_Q1_include_Letter_X(){
        String postcode = "X1 1AA";
        validatePostcode(postcode, false);
    }

    @Test
    public void testPostcode_InValid_Outward_Q2_include_Letter_I(){
        String postcode = "CI2 6XH";
        validatePostcode(postcode, false);    }
    @Test
    public void testPostcode_InValid_Outward_Q2_include_Letter_J(){
        String postcode = "CJ2 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_Q2_include_Letter_Z(){
        String postcode = "CZ2 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_Q3_include_Letter_I_onPattern_ANA(){
        String postcode = "C1I 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_Q3_include_Letter_L_onPattern_ANA(){
        String postcode = "C1L 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_Q3_include_Letter_M_onPattern_ANA(){
        String postcode = "C1M 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_Q3_include_Letter_N_onPattern_ANA(){
        String postcode = "C1N 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_Q3_include_Letter_O_onPattern_ANA(){
        String postcode = "C1o 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_Q3_include_Letter_Q_onPattern_ANA(){
        String postcode = "C1q 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_Q3_include_Letter_V_onPattern_ANA(){
        String postcode = "C1v 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_Q3_include_Letter_X_onPattern_ANA(){
        String postcode = "C1x 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_Q3_include_Letter_Y_onPattern_ANA(){
        String postcode = "C1y 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_Q3_include_Letter_Z_onPattern_ANA(){
        String postcode = "C1Z 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_onPattern_AAAA(){
        String postcode = "ABCD 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_onPattern_AAAN(){
        String postcode = "ABC1 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_onPattern_ANAA(){
        String postcode = "A1CF 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_onPattern_ANAN(){
        String postcode = "A1C9 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_onPattern_ANNA(){
        String postcode = "A11F 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_onPattern_ANNN(){
        String postcode = "A111 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_onPattern_NAAA(){
        String postcode = "1AAA 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_onPattern_NAAN(){
        String postcode = "1CF1 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_onPattern_NANA(){
        String postcode = "1A1C 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_onPattern_NANN(){
        String postcode = "2A12 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_onPattern_NNAA(){
        String postcode = "33AA 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_onPattern_NNAN(){
        String postcode = "34A1 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_onPattern_NNNA(){
        String postcode = "123A 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_onPattern_NNNN(){
        String postcode = "1234 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_onPattern_NNN(){
        String postcode = "123 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_onPattern_AAA(){
        String postcode = "abc 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_onPattern_NNA(){
        String postcode = "12a 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_onPattern_NAN(){
        String postcode = "1a3 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_Outward_onPattern_NAA(){
        String postcode = "1bb 6XH";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_postcode_outward_is5_char_length(){
        String postcode = "se1896ty";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_postcode_outward_tooSmall_noSpace(){
        String postcode = "s6tt";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_postcode_inward_tooSmall_withSpace(){
        String postcode = "ss6 tt";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_postcode_moreThan8CharLength_withSpace(){
        String postcode = "NN12 34AA";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_postcode_moreThan8CharLength_withoutSpace(){
        String postcode = "NN1234AA";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_postcode_lessThan5CharLength_withoutSpace(){
        String postcode = "W26A";
        validatePostcode(postcode, false);
    }
    @Test
    public void testPostcode_InValid_postcode_lessThan5CharLength_withSpace(){
        String postcode = "W2 6A";
        validatePostcode(postcode, false);
    }
    private void validatePostcode(String postcode , boolean expectedResult){
        Boolean valid = AddressValidator.isValidUKPostcodeFormat(postcode);
        assertThat (valid, is(expectedResult));
    }
}