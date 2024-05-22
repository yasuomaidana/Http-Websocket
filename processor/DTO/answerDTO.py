from dataclasses import dataclass


@dataclass
class AnswerDTO:
    status: str
    data: any
