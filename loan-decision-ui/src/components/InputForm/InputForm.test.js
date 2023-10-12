import React from "react";
import { shallow } from "enzyme";
import InputForm from "./InputForm";

describe("InputForm Component", () => {
  let wrapper;
  const prop = {
    change: jest.fn()
  };

  beforeEach(() => {
    wrapper = shallow(<InputForm {...prop} />);
  });

  it("renders", () => {
    expect(wrapper).not.toBeNull();
  });

  it("should update the personal code", () => {
    const personalCode = wrapper.find("#personalCode");
    personalCode.simulate("change", { target: { name: "personalCode", value: "49002010998" } });
    expect(wrapper.find("#personalCode").props().value).toEqual("49002010998");
  });

  it("should call change", () => {
    wrapper.find("button").simulate("click");
    expect(prop.change).toHaveBeenCalledTimes(1);
  });
});
