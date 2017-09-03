@extends ('layouts.admin')
@section ('contenido')
	<div class="ror">
		<div class="col-lg-6" col-md-6 col-sm-6 col-xs-12>
			<h3>Editar Categoría:  {{$articulo->nombre}}</h3>
			@if($errors->any())
			<div class="alert alert-danger" >
				<ul>
					@foreach($errors->all() as $error)
					<li>{{$error}}</li>
					@endforeach
				</ul>
			</div>
			@endif

			{!!Form::model($articulo,['method'=>'PATCH','route'=>['articulo.update',$articulo->idarticulo]])!!}
			{{Form::token()}}
			<div class="form-group">
				<label for="nombre"> ID	</label>
				<input type="text" name="nombre" class="form-control" value="{{$articulo->nombre}}" placeholder="Nombre">
			</div>
			<div class="form-group">
				<label for="nombre"> ID	</label>
				<input type="text" name="des" class="form-control" value="{{$articulo->nombre}}" placeholder="Nombre">
			</div>
			<div class="form-group">
				<button class="btn btn-primary" type="submit">Guardar</button>
				<button class="btn btn-danger" type="reset">Cancelar</button>
			</div>			
			{!!Form::close()!!}
		</div>
	</div>
@endsection