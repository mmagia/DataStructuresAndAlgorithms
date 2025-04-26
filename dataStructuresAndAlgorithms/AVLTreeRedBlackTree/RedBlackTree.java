package dataStructuresAndAlgorithms.AVLTreeRedBlackTree;/*
The author is Denis Nurmuhametov CSE-06
 */

import java.util.Scanner;

public class RedBlackTree {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = Integer.parseInt(scanner.nextLine());
        DenisNurmuhametovPair tree = new DenisNurmuhametovPair();

        for (int i = 0; i < N; i++) {
            String[] input = scanner.nextLine().split(" ");
            String operation = input[0];

            switch (operation) {
                case "ADD":
                    tree.insertPair(tree.root, Integer.parseInt(input[1]), Integer.parseInt(input[2]));
                    break;
                case "LOOKUP":
                    tree.findParentRedBlackDenisNurmuhametovTree(Integer.parseInt(input[1]));
                    break;
                case "DELETE":
                    tree.delete(Integer.parseInt(input[1]));
                    break;
                case "PRINT_ROTATIONS":
                    System.out.println(tree.rotations);
                    break;
                case "PRINT_COUNT_BLACK_KEYS":
                    System.out.println(tree.countBlackNodes(tree.root, false));
                    break;
                case "PRINT_COUNT_RED_KEYS":
                    System.out.println(tree.countBlackNodes(tree.root, true));
                    break;
            }
        }
        scanner.close();
    }
}

class DenisNurmuhametovPair {
    DenisNurmuhametovNodeRedBlack root;
    int rotations = 0;

    public void insertPair(DenisNurmuhametovNodeRedBlack whereToBeInserted, int key, int value) {
        if (whereToBeInserted == null) {
            whereToBeInserted = new DenisNurmuhametovNodeRedBlack(key, value);
            whereToBeInserted.isRed = false;
            return;
        }

        DenisNurmuhametovNodeRedBlack tmp = whereToBeInserted;
        DenisNurmuhametovNodeRedBlack parent = null;
        while (tmp != null) {
            parent = tmp;
            if (key < tmp.data.key) {
                tmp = tmp.left;
            } else if (key > tmp.data.key) {
                tmp = tmp.right;
            } else {
                System.out.println("KEY ALREADY EXISTS");
                return;
            }
        }

        DenisNurmuhametovNodeRedBlack toBeInserted = new DenisNurmuhametovNodeRedBlack(key, value);
        toBeInserted.parent = parent;
        if (key < parent.data.key) {
            parent.left = toBeInserted;
        } else {
            parent.right = toBeInserted;
        }

        DenisNurmuhametovFixInsertionViolations(toBeInserted);
    }

    private void DenisNurmuhametovFixInsertionViolations(DenisNurmuhametovNodeRedBlack toBeFixed) {
        while (toBeFixed.parent != null && toBeFixed.parent.isRed) {
            DenisNurmuhametovNodeRedBlack uncle = getUncle(toBeFixed);
            if (uncle != null && uncle.isRed) {
                toBeFixed.parent.isRed = false;
                uncle.isRed = false;
                toBeFixed.parent.parent.isRed = true;
                toBeFixed = toBeFixed.parent.parent;
            } else {
                if (toBeFixed == (toBeFixed.parent.parent.left == toBeFixed.parent ? toBeFixed.parent.right : toBeFixed.parent.left)) {
                    toBeFixed = toBeFixed.parent;
                    if (toBeFixed == toBeFixed.parent.left) {
                        rightRotate(toBeFixed);
                    } else {
                        leftRotate(toBeFixed);
                    }
                }
                toBeFixed.parent.isRed = false;
                toBeFixed.parent.parent.isRed = true;
                if (toBeFixed == toBeFixed.parent.left) {
                    rightRotate(toBeFixed.parent.parent);
                } else {
                    leftRotate(toBeFixed.parent.parent);
                }
            }
        }
        root.isRed = false;
    }

    public void findParentRedBlackDenisNurmuhametovTree(int key) {
        DenisNurmuhametovNodeRedBlack current = root;
        while (current != null) {
            if (key < current.data.key) {
                current = current.left;
            } else if (key > current.data.key) {
                current = current.right;
            } else {
                System.out.println(current.data.value);
                return;
            }
        }
        System.out.println("KEY NOT FOUND");
    }

    public void delete(int key) {
        DenisNurmuhametovNodeRedBlack node = findDenisNurmuhametovNode(root, key);
        if (node == null) {
            System.out.println("KEY NOT FOUND");
            return;
        }
        deleteDenisNurmuhametovNode(node);
    }

    private void deleteDenisNurmuhametovNode(DenisNurmuhametovNodeRedBlack node) {
        DenisNurmuhametovNodeRedBlack replaceNode;
        DenisNurmuhametovNodeRedBlack toBeReplaced;
        boolean originalColor = node.isRed;

        if (node.left == null) {
            replaceNode = node.right;
            transplant(node, node.right);
        } else if (node.right == null) {
            replaceNode = node.left;
            transplant(node, node.left);
        } else {
            replaceNode = findExchange(node.right);
            originalColor = replaceNode.isRed;
            toBeReplaced = replaceNode.right;
            if (replaceNode.parent == node) {
                if (toBeReplaced != null) {
                    toBeReplaced.parent = replaceNode;
                }
            } else {
                transplant(replaceNode, replaceNode.right);
                replaceNode.right = node.right;
                replaceNode.right.parent = replaceNode;
            }
            transplant(node, replaceNode);
            replaceNode.left = node.left;
            replaceNode.left.parent = replaceNode;
            replaceNode.isRed = node.isRed;
            if (toBeReplaced != null) {
                toBeReplaced.parent = replaceNode;
            }
        }

        if (!originalColor) {
            if (replaceNode != null) {
                DenisNurmuhametovFixDeletionViolations(replaceNode);
            }
        }
    }

    private void DenisNurmuhametovFixDeletionViolations(DenisNurmuhametovNodeRedBlack toBeFixed) {
        while (toBeFixed != root && (toBeFixed == null || !toBeFixed.isRed)) {
            DenisNurmuhametovNodeRedBlack sibling = getSibling(toBeFixed);
            if (sibling == null) {
                toBeFixed = toBeFixed == null ? toBeFixed.parent : toBeFixed.parent;
                continue;
            }

            if (sibling.isRed) {
                sibling.isRed = false;
                toBeFixed.parent.isRed = true;
                if (toBeFixed == toBeFixed.parent.left) {
                    leftRotate(toBeFixed.parent);
                } else {
                    rightRotate(toBeFixed.parent);
                }
                sibling = getSibling(toBeFixed);
            }

            if (sibling == null) {
                toBeFixed = toBeFixed.parent;
                continue;
            }

            if ((sibling.left == null || !sibling.left.isRed) &&
                    (sibling.right == null || sibling.right.isRed)) {
                sibling.isRed = true;
                toBeFixed = toBeFixed.parent;
            } else {
                if (toBeFixed == toBeFixed.parent.left && (sibling.right == null || sibling.right.isRed)) {
                    sibling.left.isRed = false;
                    sibling.isRed = true;
                    rightRotate(sibling);
                    sibling = getSibling(toBeFixed);
                } else if (toBeFixed == toBeFixed.parent.right && (sibling.left == null || sibling.left.isRed)) {
                    sibling.right.isRed = false;
                    sibling.isRed = true;
                    leftRotate(sibling);
                    sibling = getSibling(toBeFixed);
                }
                if (sibling != null) {
                    sibling.isRed = toBeFixed.parent.isRed;
                    toBeFixed.parent.isRed = false;
                    if (toBeFixed == toBeFixed.parent.left) {
                        if (sibling.right != null) sibling.right.isRed = false;
                        leftRotate(toBeFixed.parent);
                    } else {
                        if (sibling.left != null) sibling.left.isRed = false;
                        rightRotate(toBeFixed.parent);
                    }
                }
                toBeFixed = root;
            }
        }
        if (toBeFixed != null) {
            toBeFixed.isRed = false;
        }
    }

    private DenisNurmuhametovNodeRedBlack findExchange(DenisNurmuhametovNodeRedBlack exchange) {
        while (exchange.left != null) {
            exchange = exchange.left;
        }
        return exchange;
    }

    private DenisNurmuhametovNodeRedBlack findDenisNurmuhametovNode(DenisNurmuhametovNodeRedBlack node, int key) {
        if (node == null) {
            return null;
        }
        if (key < node.data.key) {
            return findDenisNurmuhametovNode(node.left, key);
        } else if (key > node.data.key) {
            return findDenisNurmuhametovNode(node.right, key);
        } else {
            return node;
        }
    }

    private void transplant(DenisNurmuhametovNodeRedBlack transplanted, DenisNurmuhametovNodeRedBlack transplantor) {
        if (transplanted.parent == null) {
            root = transplantor;
        } else if (transplanted == transplanted.parent.left) {
            transplanted.parent.left = transplantor;
        } else {
            transplanted.parent.right = transplantor;
        }
        if (transplantor != null) {
            transplantor.parent = transplanted.parent;
        }
    }

    private void leftRotate(DenisNurmuhametovNodeRedBlack node) {
        DenisNurmuhametovNodeRedBlack rightChild = node.right;
        node.right = rightChild.left;
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }
        rightChild.parent = node.parent;
        if (node.parent == null) {
            root = rightChild;
        } else if (node == node.parent.left) {
            node.parent.left = rightChild;
        } else {
            node.parent.right = rightChild;
        }
        rightChild.left = node;
        node.parent = rightChild;
        rotations++;
    }

    private void rightRotate(DenisNurmuhametovNodeRedBlack node) {
        DenisNurmuhametovNodeRedBlack leftChild = node.left;
        node.left = leftChild.right;
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }
        leftChild.parent = node.parent;
        if (node.parent == null) {
            root = leftChild;
        } else if (node == node.parent.right) {
            node.parent.right = leftChild;
        } else {
            node.parent.left = leftChild;
        }
        leftChild.right = node;
        node.parent = leftChild;
        rotations++;
    }

    private DenisNurmuhametovNodeRedBlack getUncle(DenisNurmuhametovNodeRedBlack node) {
        if (node.parent == null || node.parent.parent == null) {
            return null;
        }
        if (node.parent == node.parent.parent.left) {
            return node.parent.parent.right;
        } else {
            return node.parent.parent.left;
        }
    }

    private DenisNurmuhametovNodeRedBlack getSibling(DenisNurmuhametovNodeRedBlack node) {
        if (node.parent == null) {
            return null;
        }
        if (node == node.parent.left) {
            return node.parent.right;
        } else {
            return node.parent.left;
        }
    }

    public int countBlackNodes(DenisNurmuhametovNodeRedBlack node, boolean countRed) {
        if (node == null) {
            return 0;
        }
        int count = countBlackNodes(node.left, countRed) + countBlackNodes(node.right, countRed);
        if ((countRed && node.isRed) || (!countRed && !node.isRed)) {
            count++;
        }
        return count;
    }
}

class DenisNurmuhametovNodeRedBlack {
    DenisNurmuhametovPairRedBlack data;
    DenisNurmuhametovNodeRedBlack left;
    DenisNurmuhametovNodeRedBlack right;
    boolean isRed;
    DenisNurmuhametovNodeRedBlack parent;

    public DenisNurmuhametovNodeRedBlack(int key, int value) {
        this.data = new DenisNurmuhametovPairRedBlack(key, value);
        this.isRed = true;
    }
}

class DenisNurmuhametovPairRedBlack {
    int key;
    int value;

    public DenisNurmuhametovPairRedBlack(int key, int value) {
        this.key = key;
        this.value = value;
    }
}
