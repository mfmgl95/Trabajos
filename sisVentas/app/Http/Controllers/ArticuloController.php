<?php

namespace sisVentas\Http\Controllers;

use sisVentas\Articulo;
use Illuminate\Http\Request;
use Illuminate\Support\Fascade\Redirect;
use Illuminate\Support\Fascade\Input;
use DB;

class ArticuloController extends Controller
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
            $articulo = DB::table('articulo as a')
            ->join('categoria as c','a.idcategoria','=','c.idcategoria')
            ->select('a.idarticulo','a.nombre','a.codigo','c.nombre as categoria','a.stock','a.imagen','a.estado')
            ->where('a.nombre','LIKE','%'.$query.'%')
            ->orwhere('a.codigo','LIKE','%'.$query.'%')
            ->orderBy('a.idarticulo', 'desc')
            ->paginate(7);
            return view('almacen.articulo.index',['articulo'=>$articulo,'searchText'=>$query]);
        }
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        $categorias=DB::table('categoria')
        ->where('condicion','=','1')->get();
        return view('almacen.articulo.create',['categorias'=>$categorias]);
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
            'idcategoria' => 'required',
            'codigo' => 'required|max:50',
            'nombre' => 'required|max:100',
            'stock' => 'required|numeric',
            'descripcion' => 'required|max:512',
            'imagen' => 'mimes:jpg,jpeg,bmp,png'
            ]);
        $articulo = new Articulo;
        $articulo->idcategoria = $request -> get('idcategoria');
        $articulo->codigo = $request -> get('codigo');
        $articulo->nombre = $request -> get('nombre');
        $articulo->stock = $request -> get('stock');
        $articulo->descripcion = $request -> get('descripcion');
        $articulo->estado = 'Activo';        
        if(Input::hasFile('imagen')){
            $file=Input::file('imagen');
            $file->move(public_path().'/imagenes/articulos/',$file->getClientOriginalName());
            $articulo->imagen=$file->getClientOriginalName();
        }
        $articulo->save();
        return Redirect::to('almacen/articulo');
    }

    /**
     * Display the specified resource.
     *
     * @param  \sisVentas\Articulo  $articulo
     * @return \Illuminate\Http\Response
     */
    public function show( $id)
    {
        return view('almacen.articulo.show',['articulo'=>Articulo::findOrFail($id)]);
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \sisVentas\Articulo  $articulo
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        $articulo=Articulo::findOrFail($id);
        $categorias=DB::table('categoria')
        ->where('condicion','=','1')->get();
        return view('almacen.articulo.edit',['articulo'=>$articulo,'categorias'=>$categorias]);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \sisVentas\Articulo  $articulo
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        $request->validate([
            'idcategoria' => 'required',
            'codigo' => 'required|max:50',
            'nombre' => 'required|max:100',
            'stock' => 'required|numeric',
            'descripcion' => 'required|max:512',
            'imagen' => 'mimes:jpeg,bmp,png'
            ]);
        $articulo=Articulo::findOrFail($id);
        $articulo->idcategoria = $request -> get('idcategoria');
        $articulo->codigo = $request -> get('codigo');
        $articulo->nombre = $request -> get('nombre');
        $articulo->stock = $request -> get('stock');
        $articulo->descripcion = $request -> get('descripcion');
        $articulo->estado = 'Activo';        
        if(Input::hasFile('imagen')){
            $file=Input::file('imagen');
            $file->move(public_path().'/imagenes/articulos/',$file->getClientOriginalName());
            $articulo->imagen=$file->getClientOriginalName();
        }
        $articulo->update();
        return Redirect::to('almacen/articulo');
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \sisVentas\Articulo  $articulo
     * @return \Illuminate\Http\Response
     */
    public function destroy(Articulo $articulo)
    {
        $articulo = Articulo::findOrFail($id);
        $articulo->estado = 'Inactivo';
        $articulo->update();
        return Redirect::to('almacen/articulo');
    }
}
