export type ButtonProps = {
    content: string;
    clickEvent: () => void;
    color: "main-color1" | "main-color2" | "main-color3" | "transparent",
    height: string,
    width: string,
    borderRadius?: "rounded" | "rounded-xl",
    fontStyle?: string,
};

function Button({
    content,
    clickEvent,
    color,
    height,
    width,
    borderRadius,
    fontStyle
}: ButtonProps) {
    return (
        <button
            className={`${color} ${height} ${width} ${borderRadius} ${fontStyle}`}
            onClick={clickEvent}
        >    {content}
        </button>
    );
}

export default Button;