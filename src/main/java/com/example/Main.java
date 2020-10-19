package com.example;

import static net.bytebuddy.implementation.bytecode.assign.Assigner.Typing.DYNAMIC;

import java.lang.reflect.Method;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.asm.Advice.AllArguments;
import net.bytebuddy.asm.Advice.OnMethodEnter;
import net.bytebuddy.asm.Advice.OnMethodExit;
import net.bytebuddy.asm.Advice.Origin;
import net.bytebuddy.asm.Advice.Return;
import net.bytebuddy.asm.Advice.Thrown;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;

public class Main {

    public static void main(String[] args) {
        reloadClass(); // debug breakpoint here
        myMethod();    // debug breakpoint here
    }

    static void myMethod() {
        System.out.println("myMethod");
    }

    static void reloadClass() {
        try {
            ByteBuddyAgent.install();

            new ByteBuddy()
                .redefine(Main.class)
                .visit(Advice.to(MyAspect.class)
                    .on(MethodDescription::isMethod))
                .make()
                .load(Main.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    static class MyAspect {

        @OnMethodEnter(suppress = Throwable.class)
        public static void onEnter() {
            System.out.println("onEnter");
        }

        @OnMethodExit(suppress = Throwable.class, onThrowable = Throwable.class)
        public static void onExit(@Origin Method method,
                                  @AllArguments Object[] arguments,
                                  @Return(typing = DYNAMIC) Object returned,
                                  @Thrown Throwable exception) {
            System.out.println("onExit");
        }

    }

}
