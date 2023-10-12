import React from "react";
import { shallow } from "enzyme";
import Info from "./Info";

describe("Info Component", () => {
  let wrapper;
  const props = {
    personalCode: "49002010998",
    loanPeriodMonths: "12",
    id: "2b926f1b-db1f-45ac-af87-2130da1e1a2f",
    loanAmount: "2999.99",
    approvedAmount: "4000.00",
    deleteCard: jest.fn()
  };
  beforeEach(() => {
    wrapper = shallow(<Info {...props} />);
  });

  it("renders", () => {
    expect(wrapper).not.toBeNull();
  });

  it("renders with props", () => {
    expect(wrapper.find("[data-test='approvedAmount']").text()).toEqual("Approved Amount: 4000.00");

    expect(wrapper.find("[data-test='personalCode']").text()).toEqual(
      "Personal Code: 49002010998"
    );

    expect(wrapper.find("[data-test='loanPeriodMonths']").text()).toEqual(
      "Loan Period: 12 months"
    );

    expect(wrapper.find("[data-test='loanAmount']").text()).toEqual(
      "Requested Amount: 2999.99"
    );
  });

  it("should delete the card", () => {
    wrapper.find("button").simulate("click");

    expect(props.deleteCard).toHaveBeenCalledTimes(1);
  });
});
