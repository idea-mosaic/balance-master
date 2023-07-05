export type ButtonProps = {
    content: string;
    clickEvent: () => void;
    color: "main-color1" | "main-color2" | "main-color3" | "transparent",
    size: string,
};

function Button({
    content,
    clickEvent,
    color,
    size,
}: ButtonProps) {
    return (
        <button
            className={`${size} ${color}`}
            onClick={clickEvent}
        >    {content}
        </button>
    );
}

export default Button;