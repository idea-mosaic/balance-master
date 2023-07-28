interface Props {
  placeholder?: string;
  color?: "red" | "blue";
  maxLength?: number;
  label?: string;
  type?: string;
}

function TextInput({ placeholder, color, maxLength, label, type }: Props) {
  return (
    <div>
      {label ? <div className="p-2">{label}</div> : <></>}
      <input
        type={type}
        className={`border-2 rounded-xl p-2 w-full ${
          type === "password" ? "h-10" : "h-12"
        } ${color}`}
        placeholder={placeholder}
        maxLength={maxLength}
      />
    </div>
  );
}

export default TextInput;
