# BSKit - The Baco Swing Kit

BSKit is a comprehensive Java Swing library designed to simplify the development of rich and modern desktop applications. It provides a wide range of custom components, utilities, and a lightweight application framework to speed up your development process.

## Features

BSKit comes packed with features to help you build beautiful and functional user interfaces:

### Rich Component Library

*   **Advanced Text Fields**: Includes specialized fields for dates (`BSDateField`), money (`BSMoneyField`), numbers (`BSNumberField`), and time (`BSTimeField`).
*   **Autocomplete ComboBox**: A combo box (`BSAutoCompleteComboBox`) with built-in autocomplete functionality.
*   **Modern Boolean Switch**: A sleek toggle switch (`BSBoolSwitch`) as a modern alternative to checkboxes.
*   **Navigation Components**: A breadcrumb component (`BSBreadcrumb`) for easy navigation.
*   **Enhanced Tables**: A filterable table (`BSFilteredTable`) and a table with groupable headers (`BSGroupableTableHeader`).
*   **Stylish Panels**: A panel with rounded corners (`BSRoundedPanel`) for a modern look.
*   **Search Fields**: A dedicated search text field (`BSSearchTextField`).
*   **Advanced Password Field**: A more secure and user-friendly password widget (`BSPasswordWidget`).

### Application Framework

*   **Application Core**: A simple application framework (`BSCoreFactory`) to manage the application lifecycle.
*   **Session Management**: Built-in session management (`BSSessionManager`) to handle user sessions.
*   **Dialogs and Windows**: A set of pre-built dialogs (`BSDlgError`, `BSDlgInfo`, etc.) for common interactions.
*   **Animated Containers**: Components like `BSAnimatedSplit` to create fluid user experiences.

### Data Management

*   **Bean-based Table Models**: Easily populate tables from your Java objects using `BSBeanTableModel`.
*   **Scaffolding**: (Experimental) Automatically generate UI from your data models using annotations.

### Look and Feel

*   **Theming**: Easily customizable look and feel using the JTattoo library.

## Getting Started

### Using with Maven

To use BSKit in your project, add the following dependency to your `pom.xml`:

```xml
<dependency>
  <groupId>com.baco.commons</groupId>
  <artifactId>BSKit</artifactId>
  <version>4.8-SNAPSHOT</version>
</dependency>
```

**Note:** BSKit may not be available in public Maven repositories. You might need to configure your `settings.xml` to point to the appropriate repository, or build the project from source and install it locally.

### Building from Source

You can also build BSKit from the source code.

1.  **Clone the repository:**
    ```sh
    git clone <repository-url>
    cd BSKit
    ```

2.  **Build with Maven:**
    ```sh
    mvn clean install
    ```
    This will build the project and install the JAR file in your local Maven repository.

## Running the Demo

BSKit includes a demo application that showcases its features. To run the demo:

1.  Make sure you have built the project from source as described above.
2.  Run the `BSMainDemo` class from your IDE or using the following Maven command:

    ```sh
    mvn exec:java -Dexec.mainClass="com.baco.ui.demo.BSMainDemo"
    ```

This will launch the demo application, where you can see the various components and features in action.
