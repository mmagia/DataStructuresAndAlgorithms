package dataStructuresAndAlgorithms.AVLTreeRedBlackTree;
/*
The author is Denis Nurmuhametov
 */
import java.util.Scanner;

public class AVLTree {

    public static void main(String[] args) {
        DenisNurmuhametovAVLTree tree = new DenisNurmuhametovAVLTree();
        Scanner scanner = new Scanner(System.in);
        int N = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < N; i++) {
            String[] data = scanner.nextLine().split(" ");
            String operation = data[0];
            switch (operation) {
                case "ADD":
                    tree.root = tree.insertDenisNurmuhametovPair(tree.root, Integer.parseInt(data[1]), Integer.parseInt(data[2]));
                    break;
                case "LOOKUP":
                    tree.findDenisNurmuhametovPair(tree.root, Integer.parseInt(data[1]));
                    break;
                case "DELETE":
                    tree.root = tree.deleteDenisNurmuhametovPair(tree.root, Integer.parseInt(data[1]));
                    break;
                case "PRINT_ROTATIONS":
                    System.out.println(tree.rotations);
                    break;
            }
        }
        scanner.close();
    }
}

class DenisNurmuhametovAVLTree {
    DenisNurmuhametovNode root;
    int rotations;

    int getHeight(DenisNurmuhametovNode node) {
        return node == null ? 0 : node.height;
    }

    int balanceFactor(DenisNurmuhametovNode node) {
        return node == null ? 0 : getHeight(node.left) - getHeight(node.right);
    }

    DenisNurmuhametovNode rightRotateDenisNurmuhametovAVLTree(DenisNurmuhametovNode toBeRotated) {
        DenisNurmuhametovNode newRoot = toBeRotated.left;
        DenisNurmuhametovNode tmp = newRoot.right;

        newRoot.right = toBeRotated;
        toBeRotated.left = tmp;

        toBeRotated.height = Math.max(getHeight(toBeRotated.left), getHeight(toBeRotated.right)) + 1;
        newRoot.height = Math.max(getHeight(newRoot.left), getHeight(newRoot.right)) + 1;

        rotations++;
        return newRoot;
    }

    DenisNurmuhametovNode leftRotateDenisNurmuhametovAVLTree(DenisNurmuhametovNode toBeRotated) {
        DenisNurmuhametovNode newRoot = toBeRotated.right;
        DenisNurmuhametovNode tmp = newRoot.left;

        newRoot.left = toBeRotated;
        toBeRotated.right = tmp;

        toBeRotated.height = Math.max(getHeight(toBeRotated.left), getHeight(toBeRotated.right)) + 1;
        newRoot.height = Math.max(getHeight(newRoot.left), getHeight(newRoot.right)) + 1;

        rotations++;
        return newRoot;
    }

    DenisNurmuhametovNode insertDenisNurmuhametovPair(DenisNurmuhametovNode whereToBeInserted, int key, int value) {
        if (whereToBeInserted == null) {
            return new DenisNurmuhametovNode(key, value);
        }

        if (key > whereToBeInserted.data.key) {
            whereToBeInserted.right = insertDenisNurmuhametovPair(whereToBeInserted.right, key, value);
        } else if (key < whereToBeInserted.data.key) {
            whereToBeInserted.left = insertDenisNurmuhametovPair(whereToBeInserted.left, key, value);
        } else {
            System.out.println("KEY ALREADY EXISTS");
            return whereToBeInserted;
        }

        whereToBeInserted.height = 1 + Math.max(getHeight(whereToBeInserted.left), getHeight(whereToBeInserted.right));

        int balance = balanceFactor(whereToBeInserted);

        if (balance > 1 && key < whereToBeInserted.left.data.key) {
            return rightRotateDenisNurmuhametovAVLTree(whereToBeInserted);
        }

        if (balance < -1 && key > whereToBeInserted.right.data.key) {
            return leftRotateDenisNurmuhametovAVLTree(whereToBeInserted);
        }

        if (balance > 1 && key > whereToBeInserted.left.data.key) {
            whereToBeInserted.left = leftRotateDenisNurmuhametovAVLTree(whereToBeInserted.left);
            return rightRotateDenisNurmuhametovAVLTree(whereToBeInserted);
        }

        if (balance < -1 && key < whereToBeInserted.right.data.key) {
            whereToBeInserted.right = rightRotateDenisNurmuhametovAVLTree(whereToBeInserted.right);
            return leftRotateDenisNurmuhametovAVLTree(whereToBeInserted);
        }

        return whereToBeInserted;
    }

    DenisNurmuhametovNode findExchange(DenisNurmuhametovNode node) {
        DenisNurmuhametovNode exchange = node;
        while (exchange.left != null) {
            exchange = exchange.left;
        }
        return exchange;
    }

    DenisNurmuhametovNode deleteDenisNurmuhametovPair(DenisNurmuhametovNode toBeDeleted, int key) {
        if (toBeDeleted == null) {
            System.out.println("KEY NOT FOUND");
            return null;
        }

        if (key < toBeDeleted.data.key) {
            toBeDeleted.left = deleteDenisNurmuhametovPair(toBeDeleted.left, key);
        } else if (key > toBeDeleted.data.key) {
            toBeDeleted.right = deleteDenisNurmuhametovPair(toBeDeleted.right, key);
        } else {
            if (toBeDeleted.left == null || toBeDeleted.right == null) {
                DenisNurmuhametovNode temp = (toBeDeleted.left != null) ? toBeDeleted.left : toBeDeleted.right;
                if (temp == null) {
                    toBeDeleted = null;
                } else {
                    toBeDeleted = temp;
                }
            } else {
                DenisNurmuhametovNode temp = findExchange(toBeDeleted.right);
                toBeDeleted.data = temp.data;
                toBeDeleted.right = deleteDenisNurmuhametovPair(toBeDeleted.right, temp.data.key);
            }
        }

        if (toBeDeleted == null) {
            return null;
        }

        toBeDeleted.height = 1 + Math.max(getHeight(toBeDeleted.left), getHeight(toBeDeleted.right));

        int balance = balanceFactor(toBeDeleted);

        if (balance > 1 && balanceFactor(toBeDeleted.left) >= 0) {
            return rightRotateDenisNurmuhametovAVLTree(toBeDeleted);
        }

        if (balance > 1 && balanceFactor(toBeDeleted.left) < 0) {
            toBeDeleted.left = leftRotateDenisNurmuhametovAVLTree(toBeDeleted.left);
            return rightRotateDenisNurmuhametovAVLTree(toBeDeleted);
        }

        if (balance < -1 && balanceFactor(toBeDeleted.right) <= 0) {
            return leftRotateDenisNurmuhametovAVLTree(toBeDeleted);
        }

        if (balance < -1 && balanceFactor(toBeDeleted.right) > 0) {
            toBeDeleted.right = rightRotateDenisNurmuhametovAVLTree(toBeDeleted.right);
            return leftRotateDenisNurmuhametovAVLTree(toBeDeleted);
        }

        return toBeDeleted;
    }

    void findDenisNurmuhametovPair(DenisNurmuhametovNode toBeFound, int key) {
        if (toBeFound == null) {
            System.out.println("KEY NOT FOUND");
            return;
        }

        if (key < toBeFound.data.key) {
            findDenisNurmuhametovPair(toBeFound.left, key);
        } else if (key > toBeFound.data.key) {
            findDenisNurmuhametovPair(toBeFound.right, key);
        } else {
            System.out.println(toBeFound.data.value);
        }
    }
}


class DenisNurmuhametovNode {
    DenisNurmuhametovPairRedBlack data;
    int height;
    DenisNurmuhametovNode left;
    DenisNurmuhametovNode right;

    DenisNurmuhametovNode(int key, int value) {
        data = new DenisNurmuhametovPairRedBlack(key, value);
        this.height = 1;
    }
}


