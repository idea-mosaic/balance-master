import Button from "@/components/Button";

export default function Home() {
  return (
    <>
      <Button
        clickEvent={function (): void {
          alert("CLICKED!!");
        }}
        content={"MAIN COLOR 1"}
        color={"main-color1"}
        size={"sm-btn"}
      ></Button>
      <Button
        clickEvent={function (): void {
          alert("CLICKED!!");
        }}
        content={"MAIN COLOR 2"}
        color={"main-color2"}
        size={"md-btn"}
      ></Button>
      <p>HOME</p>
    </>
  );
}
