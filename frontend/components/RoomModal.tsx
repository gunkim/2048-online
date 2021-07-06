import React, { useState } from "react"
import { Form, Input, Modal, Radio } from "antd"

const layout = {
  labelCol: { span: 4 },
  wrapperCol: { span: 24 }
}
const RoomModal = ({ isModalVisible, handleOk, handleCancel }) => {
  const [form, setForm] = useState({
    title: "",
    peoples: "four",
    gameMode: "speed"
  })

  const handleChange = e => {
    const name = e.target.name
    const value = e.target.value

    setForm({
      ...form,
      [name]: value
    })
  }

  return (
    <div>
      <Modal
        title="방 만들기"
        visible={isModalVisible}
        onOk={handleOk}
        onCancel={handleCancel}
        cancelText="취소"
        okText="완료"
      >
        <Form {...layout}>
          <Form.Item label="제목">
            <Input name="title" value={form.title} onChange={handleChange} />
          </Form.Item>
          <Form.Item name="radio-group" label="인원수">
            <Radio.Group
              name="peoples"
              value={form.peoples}
              onChange={handleChange}
            >
              <Radio value="four">4명</Radio>
              <Radio value="two">2명</Radio>
            </Radio.Group>
          </Form.Item>
          <Form.Item name="radio-group" label="게임 모드">
            <Radio.Group
              name="gameMode"
              value={form.gameMode}
              onChange={handleChange}
            >
              <Radio value="speed">스피드</Radio>
              <Radio value="survival">서바이벌</Radio>
              <Radio value="time">타임어택</Radio>
            </Radio.Group>
            <div>
              <b>모드 설명 : </b>
              {form.gameMode == "speed" ? (
                <p>
                  빠르게 가장 먼저{" "}
                  <span style={{ color: "blue", fontWeight: "bold" }}>
                    2048
                  </span>
                  을 만든 사람이 승리하는 모드입니다.
                </p>
              ) : (
                ""
              )}
              {form.gameMode == "survival" ? (
                <p>
                  <span style={{ color: "blue", fontWeight: "bold" }}>
                    마지막
                  </span>
                  까지 생존한 사람이 승리하는 모드입니다.
                </p>
              ) : (
                ""
              )}
              {form.gameMode == "time" ? (
                <p>
                  지정된 시간 안에{" "}
                  <span style={{ color: "blue", fontWeight: "bold" }}>
                    가장 높은 점수
                  </span>
                  를 기록한 사람이 승리하는 모드입니다.
                </p>
              ) : (
                ""
              )}
            </div>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  )
}

export default RoomModal
