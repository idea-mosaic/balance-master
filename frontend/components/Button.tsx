export type ButtonProps = {
    content: string;
    clickEvent: () => void;
    color: string,
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