package dataStructuresAndAlgorithms.StackQueue;
//The author is Nurmuhametov Denis

import java.util.Scanner;


public class PostfixToInteger {
    private static denisQueue<String> queue = new denisQueue<>();
    private static denisStack<String> stack = new denisStack<>();
    private static denisStack<String> evaluatingStack = new denisStack<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String input = scanner.nextLine();
        denisShuntingYardAlgorithm(input.split(" "));
        String[] array = queue.fromQueueToStringArray();
        int answer = denisEvaluatePostfixExpression(array);
        System.out.println(answer);
    }


    public static void printArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
    }

    public static int denisEvaluatePostfixExpression(String[] expr) {
        for (int i = 0; i < expr.length; i++) {
            if (isNumber(expr[i])) {
                stack.push(expr[i]);
            } else if (isOperator(expr[i]) || isFunction(expr[i])) {
                int firstOperand = Integer.parseInt(stack.pop());
                int secondOperand = Integer.parseInt(stack.pop());
                switch (expr[i]) {
                    case "+":
                        stack.push(String.valueOf(firstOperand + secondOperand));
                        break;
                    case "-":
                        stack.push(String.valueOf(secondOperand - firstOperand));
                        break;
                    case "*":
                        stack.push(String.valueOf(firstOperand * secondOperand));
                        break;
                    case "/":
                        stack.push(String.valueOf(secondOperand / firstOperand));
                        break;
                    case "max":
                        stack.push(String.valueOf(max(firstOperand, secondOperand)));
                        break;
                    case "min":
                        stack.push(String.valueOf(min(firstOperand, secondOperand)));
                        break;
                }
            }
        }
        return Integer.parseInt(stack.pop());
    }


    public static int max(int x, int y) {
        if (x > y) {
            return x;
        }
        return y;
    }

    public static int min(int x, int y) {
        if (x < y) {
            return x;
        }
        return y;
    }


    public static void denisShuntingYardAlgorithm(String[] input) {
        for (String token : input) {
            if (isNumber(token)) {
                queue.offer(token);
            } else if (isFunction(token)) {
                stack.push(token);
            } else if (isOperator(token)) {
                while (!stack.isEmpty() && isOperator(stack.peek()) && precedence(token) <= precedence(stack.peek())) {
                    queue.offer(stack.pop());
                }
                stack.push(token);
            } else if (token.equals(",")) {
                while (!stack.isEmpty() && isOperator(stack.peek())) {
                    queue.offer(stack.pop());
                }
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && isOperator(stack.peek())) {
                    queue.offer(stack.pop());
                }
                stack.pop();
                if (!stack.isEmpty()) {
                    if (isFunction(stack.peek())) {
                        queue.offer(stack.pop());
                    }
                }
            }
        }

        while (!stack.isEmpty()) {
            queue.offer(stack.pop());
        }
    }


    public static boolean isNumber(String token) {
        if (token.equals("0") || token.equals("1") || token.equals("2") || token.equals("3") || token.equals("4") ||
                token.equals("5") || token.equals("6") || token.equals("7") || token.equals("8") || token.equals("9")) {
            return true;
        }
        return false;
    }

    public static boolean isFunction(String token) {
        if (token.equals("max") || token.equals("min")) {
            return true;
        }
        return false;
    }


    public static boolean isOperator(String token) {
        if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
            return true;
        }
        return false;
    }

    public static int precedence(String token) {
        if (token.equals("+") || token.equals("-")) {
            return 1;
        } else if (token.equals("*") || token.equals("/")) {
            return 2;
        }
        return -1;
    }

}


