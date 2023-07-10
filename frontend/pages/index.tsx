import Button from "@/components/Button";
import ItemSelect from "@/components/ItemSelect";

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
      <p>ItemSelect 컴포넌트 테스트</p>
      <ItemSelect selectColor={"red"} selectTitle="짬뽕" selectDesc="짬뽕이 최고지!"></ItemSelect>
     
      <br />
      <ItemSelect
        selectColor={"blue"}
        selectImage="https://recipe1.ezmember.co.kr/cache/recipe/2017/10/22/aaeb2a235b89ac305ba919e33da2e6331.jpg"
        selectTitle="짬뽕"
        selectRatio={64}
        selectDesc="짬뽕이 최고지!"
      ></ItemSelect>
      <br />
      <ItemSelect
        selectColor={"blue"}
        selectImage="https://recipe1.ezmember.co.kr/cache/recipe/2017/10/22/aaeb2a235b89ac305ba919e33da2e6331.jpg"
        selectTitle="짬뽕"
        selectDesc="짬뽕이 최고지!"
      ></ItemSelect>
    </>
  );
}
