interface Props {
  placeholder?: string;
  color?: "red" | "blue";
  maxLength?: number;
}

function TextInput({ placeholder, color, maxLength }: Props) {
  return (
    <input
      type="text"
      className={`border-2 rounded-lg p-2 w-full h-12 ${color}`}
      placeholder={placeholder}
      maxLength={maxLength}
    />
  );
}

export default TextInput;
