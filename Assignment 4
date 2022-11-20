import java.util.List;
import java.util.Map;

enum PieceColor {
    WHITE,
    BLACK;

    public PieceColor parse(String color) {
        if (color.equals("White")) {
            return WHITE;
        }
        if (color.equals("Black")) {
            return BLACK;
        }
        return InvalidPieceColorException();
    }
}
abstract class Position {
    private int x;
    private int y;
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
interface BishopMovement{
   default int getDiagonalMovesCount() {
       return 0;
   }
}
interface RookMovement{
   default int getHorizontalMovesCount() {
       return 0;
   }
   default int getVerticalMovesCount() {
       return 0;
   }
}

class Board {
    private Map<String, ChessPiece> piecePosition;
    private int size;
}

abstract class ChessPiece {
    protected PieceColor color;
    protected Position position;
    public ChessPiece(PieceColor color, Position position) {
        this.color = color;
        this.position = position;
    }
    public PieceColor getColor() {
        return color;
    }
    public Position getPosition() {
        return position;
    }
    public int getMovesCount(Map<String, ChessPiece> position, int boardSize) {
        return 0;
    }
    public int getCapturesCount(Map<String, ChessPiece> position, int boardSize) {
        return 0;
    }


}

class Exception{
    public String getMessages(){
        return "Invalid Position";
    }
}
class InvalidPiecePositionException extends Exception{
    public String getMessages(){
        return "Invalid Position";
    }
}
class InvalidBoardSizeException extends Exception{
    public String getMessages(){
        return "Invalid Board Size";
    }
}
class InvalidNumberOfPiecesException extends Exception{
    public String getMessages(){
        return "Invalid Number of Pieces";
    }
}
class InvalidPieceColorException extends Exception{
    public String getMessages(){
        return "Invalid Piece Color";
    }
}
class InvalidPieceNameException extends Exception{
    public String getMessages(){
        return "Invalid Piece Name";
    }
}
class InvalidInputException extends Exception{
    public String getMessages(){
        return "Invalid Input";
    }
}
class InvalidGivenKingsException extends Exception{
    public String getMessages(){
        return "Invalid Given Kings";
    }
}

