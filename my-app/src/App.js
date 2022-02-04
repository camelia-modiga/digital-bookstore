import React from "react";
import './App.css';
class App extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            items: [],
            DataisLoaded: false
        };
    }

    componentDidMount() {
        fetch(
            "http://localhost:8080/api/bookcollection/books")
            .then((res) => res.json())
            .then((json) => {
                this.setState({
                    items: Object.entries(Object.entries(json)[0][1])[0][1],
                    DataisLoaded: true
                });
            })
    }

    render() {

        const { DataisLoaded, items } = this.state;

        if (!DataisLoaded) {
            return (
                <div >
                    <h1 > Please wait some time.... </h1>
                </div >
            );
        }

        return (
            <div className="App" >

                <h1 > Fetch data from an api in react </h1>
                <table >
                    <tr >
                        <th > ISBN </th>
                        <th > Title </th>
                        <th > Publisher </th>
                        <th > Year </th>
                        <th > Genre </th>
                        <th > Price </th>
                        <th > Stock </th>
                    </tr >
                    {

                        items.map((item) => (


                            <tr>
                                <td > {item.isbn} </td>
                                <td > {item.title} </td>
                                <td > {item.publisher} </td>
                                <td > {item.year} </td>
                                <td > {item.genre} </td>
                                <td > {item.price} </td>
                                <td > {item.stock} </td>

                            </tr>
                        ))
                    }
                </table>
            </div >
        );
    }
}

export default App;