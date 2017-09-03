<?php

namespace sisVentas\Http\Controllers;

use sisVentas\Categoria;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Redirect;
use DB;


class CategoriaController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index(Request $request)
    {
        if($request)
        {
            $query = trim($request -> get('searchText'));
            $categoria = DB::table('categoria')
            ->where('nombre','LIKE','%'.$query.'%')
            ->where('condicion','=','1')
            ->orderBy('idcategoria','asc')
            ->paginate(7);
            return view('almacen.categoria.index',['categoria'=>$categoria,'searchText'=>$query]);
        }
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        return view('almacen.categoria.create');
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $request->validate([
            'nombre' => 'required|max:50',
            'descripcion' => 'max:256',
            ]);

        
        $categoria = new Categoria;
        $categoria->nombre = $request -> get('nombre');
        $categoria->descripcion = $request -> get('descripcion');
        $categoria->condicion = '1';
        $categoria->save();
        return Redirect::to('almacen/categoria');
    }

    /**
     * Display the specified resource.
     *
     * @param  \sisVentas\Categoria  $categoria
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        return view('almacen.categoria.show',['categoria'=>Categoria::findOrFail($id)]);
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \sisVentas\Categoria  $categoria
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        return view('almacen.categoria.edit',['categoria'=>Categoria::findOrFail($id)]);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \sisVentas\Categoria  $categoria
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        $request->validate([
            'nombre' => 'required|max:50',
            'descripcion' => 'max:256',
            ]);
        
        $categoria=Categoria::findOrFail($id);
        $categoria->nombre=$request->get('nombre');
        $categoria->descripcion=$request->get('descripcion');
        $categoria->update();
        return Redirect::to('almacen/categoria');
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \sisVentas\Categoria  $categoria
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $categoria = Categoria::findOrFail($id);
        $categoria->condicion = '0';
        $categoria->update();
        return Redirect::to('almacen/categoria');
    }
}
