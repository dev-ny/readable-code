package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gamelevel.Advanced;
import cleancode.minesweeper.tobe.gamelevel.Beginner;
import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;

public class GameApplication {

    public static void main(String[] args) {
        GameLevel gameLevel = new Beginner();
        InputHandler inputHandler = new ConsoleInputHandler();
        OutputHandler outputHandler = new ConsoleOutputHandler();

        Minesweeper minesweeper = new Minesweeper(gameLevel, inputHandler, outputHandler);
        minesweeper.initialize();
        minesweeper.run();
    }


    /**
     * DIP (Dependency Inversion Principle)
     * 스프링의 3대 요소
     * IOC, DI
     * PSA, AOP
     *
     * DI = Depedency Injection
     *
     * IoC = Inversion of Control
     *
     * DIP 는 고수준 모듈과 저수준 모듈이 직접적으로 의존하는 것이 아닌 추상화에 서로 의존해야 한다.
     * DI 는 의존성을 주입한다. 필요한 의존성을 내가 직접 생성하는 것이 아니라 외부에서 주입받겠다. DI 를 생각하면 떠올라야하는 숫자가 있음. 3
     * 객체(A)가 있고, 또 다른 객체(B) 하나가 있음. A 객체가 B 객체를 필요로 함. 이 둘이 의존성을 갖고 싶은데, A가 B를 생성해서 사용하는게 아니라 의존성을 주입받고 싶음.
     * 생성자나 다른 메소드를 통해 주입받고자 할때 A와 B 는 주입하는 걸 할 수 있음. 그러니 제 3자가 A와 B의 의존성을 맺어줄 수 밖에 없음.
     * Spring 에서는 이걸 "Spring Context"="IOC Container" 가 해줌.
     * 이 것들(객체의 결정과 주입)이 Runtime 에 실행됨
     *
     * DI 와 붙어다니는 IoC
     * IoC 는 제어의 역전. Spring 에서만 사용되지 않음. 더 큰 개념
     * 프로그램의 흐름을 개발자가 아닌 프레임워크가 담당하도록 하는 것.
     * 제어의 순방향 = 프로그램은 개발자가 만드는 것 = 내가 만든 프로그램 개발자가 제어 -> 근데 이 제어 흐름이 역전됐다.
     * 내가 만든 프로그램이 미리 만들어진 공장같은 프레임워크가 있고 프레임워크 안에 요소로 내 코드가 들어가서 일부분 톱니바퀴의 하나처럼 동작하는 것.
     * 프레임워크는 톱니바퀴만 빠져있는 것. 내가 톱니바퀴만 하나 만들어서 내 애플리케이션이야! 하면서 뾱하고 끼우면 됨.
     * 이땐 프레임워크가 메인이 되는 것임. 내 코드는 프레임 워크의 일부가 되면서. 제어가 프레임워크 쪽으로 넘어가는 것
     * Spring Framework = 코드를 입력했을때 Spring 이 제공하는 여러가지 기능을 사용하면서 규격에 맞춰서 코딩함 -> 이런 것들이 제어의 역전
     *
     * 객체의 레벨에서 보면 IoC 컨테이너라는 친구가 객체를 직접적으로 생성해주고 생명주기를 관리해 줌.
     * MineSweeper 가 아까는 ConsoleInputHandler 를 class 내에서 생성하고 사용함.
     * IoC 컨테이너가 하는 일은. "생성과 소멸은 내가 알아서 해줄게" "객체 자체의 생명주기도 내가 알아서 다 해줄게, 의존성 주입도 DI 로 해줄게"
     * "너는 쓰기만 해!!!"
     *
     * 객체레벨에서도 프로그램 제어권이 IoC 컨테이너라고 하는 친구에게 주도권이 있기 때문에 객체의 생성들 즉,
     * Spring 에서는 Spring 이 관리하는 객체들을 Bean 이라고 하는데 Spring 에서는 이 Bean 들을 생성하고 Bean 들끼리 의존성을 주입해주고
     * 생명주기를 관리하는 일을 IoC 컨테이너가 하게 됨.
     *
     * @Component, @Service -> 내가 직접 생성 안함. IoC 컨테이너가 생성해줌.
     */
}