interface Props {
  placeholder?: string;
  color?: "red" | "blue";
  maxLength?: number;
  height?: string;
}

function TextareaInput({ placeholder, color, maxLength, height }: Props) {
  return (
    <textarea
      className={`border-2 rounded-lg p-2 w-full resize-none ${height} ${color}`}
      placeholder={placeholder}
      maxLength={maxLength}
    />
  );
}

export default TextareaInput;
