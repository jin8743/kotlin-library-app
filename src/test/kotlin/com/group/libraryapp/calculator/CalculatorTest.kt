package com.group.libraryapp.calculator


import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CalculatorTest {

    @Test
    fun addTest() {
        //given
        val calculator = Calculator(5)

        //when
        calculator.add(6)

        //then
        assertThat(calculator.number).isEqualTo(11)
    }

    @Test
    fun minusTest() {
        //given
        val calculator = Calculator(10)

        //when
        calculator.minus(9)

        //then
        assertThat(calculator.number).isEqualTo(1)
    }

    @Test
    fun multiplyTest() {
        //given
        val calculator = Calculator(10)

        //when
        calculator.multiply(9)

        //then
        assertThat(calculator.number).isEqualTo(90)
    }

    @Test
    fun divideSuccessTest() {
        //given
        val calculator = Calculator(10)

        //when
        calculator.divide(2)

        //then
        assertThat(calculator.number).isEqualTo(5)
    }

    @Test
    fun divideFailTest() {
        //given
        val calculator = Calculator(10)

        //when then
        assertThrows<IllegalArgumentException> {
            calculator.divide(0)
        }
    }
}