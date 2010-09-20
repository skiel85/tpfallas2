# file: AWTTest.jy
#

#
# Import the AWT classes.
#

from java.awt import Frame
from java.awt import Panel
from java.awt import Button
from java.awt import TextArea
from java.awt.event import ActionListener


#
# Define the TestButtonAction class. TestButtonAction
# inherits from the Java ActionListener interface.
#

class TestButtonAction(ActionListener):

        def actionPerformed(self, e):
                textArea.append("Test Button Clicked!\n")


#
# Create the Frame, Panel, Button,
# TextArea, and TestButtonAction objects.
#

frame = Frame("Hello World")
panel = Panel()
button = Button("Test Button")
buttonAction = TestButtonAction()
textArea = TextArea()


#
# Put everything together and show
# the window.
#

button.addActionListener(buttonAction)
panel.add(button)
panel.add(textArea)
frame.add(panel)
frame.pack()
frame.show()
