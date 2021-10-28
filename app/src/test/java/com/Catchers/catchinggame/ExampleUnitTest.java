package com.Catchers.catchinggame;

import org.junit.Test;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    //weather or not we can create spirit target
    @Test
    public void createSpiritTarget(){
        SpiritTarget Target = new SpiritTarget();
        assertNotNull(Target);
    }

    //testing to ensure the velocity is setting correctly
    @Test
    public void SpiritTarget_setVelocity(){
        SpiritTarget Target = new SpiritTarget();
        assertNotNull(Target);
        Target.SetVelocity(2, 4);
        assertEquals(2, Target.GetVelocity()[0], 0.01f);
        assertEquals(4, Target.GetVelocity()[1], 0.01f);
    }
    @Test
    public void SpiritTarget_setVelocity1(){
        SpiritTarget Target = new SpiritTarget();
        assertNotNull(Target);
        Target.SetVelocity(3.1f, -6.2f);
        assertEquals(3.1f, Target.GetVelocity()[0], 0.01f);
        assertEquals(-6.2f, Target.GetVelocity()[1], 0.01f);
    }
    //negative test
    //expected it to fail which is why i tried throwing invalid value
    @Test
    public void SpiritTarget_setVelocityFail(){
        SpiritTarget Target = new SpiritTarget();
        assertNotNull(Target);
        assertThrows(InvalidParameterException.class, () -> Target.SetVelocity(3000, -6.2f));

    }

}
