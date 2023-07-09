import Button from "@/components/Button";

export default function Home() {
  return (
    <>
      <p>버튼 컴포넌트 테스트</p>
      <span>등록, 수정 버튼</span>
      <Button
        clickEvent={function (): void {
          alert("CLICKED!!");
        }}
        content={"등록"}
        color={"main-color3"}
        height={"h-10"}
        width={"w-20"}
        borderRadius="rounded-xl"
        fontStyle="font-bold text-white"
      ></Button>
    </>
  );
}
